`timescale 1us / 1ps

module ARTAU(
    input CLK,
    input RST,
    input scan_for_target,
    input radar_echo,
    input [31:0] jet_speed,
    input [31:0] max_safe_distance,
    output reg radar_pulse_trigger,
    output reg [31:0] distance_to_target,
    output reg threat_detected,
    output reg [1:0] ARTAU_state
);

  // Define states
  parameter IDLE = 2'b00;
  parameter EMIT = 2'b01;
  parameter LISTEN = 2'b10;
  parameter ASSESS = 2'b11;

  
  reg zort = 0;
  reg asses = 0;
  reg backToIdle = 0;

  reg [31:0] start_time;
  reg[31:0] listen_timer;
  reg[31:0] assess_timer;

  reg [31:0] previous_distance;
  reg [31:0] relative_velocity;

  always @(posedge CLK or posedge RST) begin
    if (RST) begin
      ARTAU_state <= IDLE;
      radar_pulse_trigger <= 0;
      distance_to_target <= 0;
      threat_detected <= 0;
      start_time <= 0;
      previous_distance <= 0;
      relative_velocity <= 0;
    end else begin
      case (ARTAU_state)
        IDLE: begin
          if (scan_for_target | radar_pulse_trigger) begin
            if(radar_pulse_trigger == 0) begin
              radar_pulse_trigger <= 1;
            end
            
            ARTAU_state <= EMIT;
          end
        end
        EMIT: begin
          if(radar_pulse_trigger == 0)begin
              ARTAU_state <= LISTEN;
          end
        end
        LISTEN : begin
          if(radar_pulse_trigger)begin
            ARTAU_state = EMIT;
          end
          else if (asses) begin
            ARTAU_state = ASSESS;
          end
        end
        ASSESS : begin
          if(backToIdle)begin
            ARTAU_state = IDLE;
          end
        end
        

        

        
        
      endcase
    end
  end
always @(negedge CLK or radar_pulse_trigger or scan_for_target or posedge radar_echo) begin
      case (ARTAU_state)
        IDLE: begin
          if (scan_for_target) begin
            radar_pulse_trigger <= 1;
            start_time <= $stime;
          end
        end
        EMIT: begin
            if($stime - start_time >= 300)begin
              radar_pulse_trigger <= 0;
              listen_timer = $stime;
            end
        end
        LISTEN: begin
          if (radar_echo) begin
            if (!zort) begin
              distance_to_target <= (($stime - listen_timer) * 300_000_000) / 2_000_000;
              previous_distance = distance_to_target;
              distance_to_target = 0;
              radar_pulse_trigger <= 1;
              start_time = $stime;
              zort = 1;
            end
            else if(zort) begin
              assess_timer = $stime;
              distance_to_target <= (($stime - listen_timer) * 300) / 2;
            relative_velocity <= ($signed(distance_to_target + jet_speed * ($stime - start_time)) - previous_distance) < 0 ? 
        -($signed(distance_to_target + jet_speed * ($stime - start_time)) - previous_distance) : 
        ($signed(distance_to_target + jet_speed * ($stime - start_time)) - previous_distance);
        threat_detected <= (relative_velocity > 0) && (distance_to_target < max_safe_distance);
        asses = 1;
        
            end
          end
          else if ($stime - listen_timer >= 2000) begin
            ARTAU_state = IDLE;
          end
        end
        ASSESS: begin
          if($stime - assess_timer >= 3000)begin
            distance_to_target = 0;
            threat_detected = 0;
            backToIdle = 1;
            
          end
        end
        
      endcase
    end
  
endmodule