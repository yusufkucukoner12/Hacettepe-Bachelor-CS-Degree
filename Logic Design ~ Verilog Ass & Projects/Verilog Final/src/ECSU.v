`timescale 1us / 1ps

module ECSU (
    input CLK,
    input RST,
    input thunderstorm,
    input [5:0] wind,
    input [1:0] visibility,
    input signed [7:0] temperature,
    output reg severe_weather,
    output reg emergency_landing_alert,
    output reg [1:0] ECSU_state
);

  // Define states
  parameter ALL_CLEAR = 2'b00;
  parameter CAUTION = 2'b01;
  parameter HIGH_ALERT = 2'b10;
  parameter EMERGENCY = 2'b11;

always @(posedge CLK or posedge RST) begin
    if (RST) begin
      ECSU_state = ALL_CLEAR;
      severe_weather = 0;
      emergency_landing_alert = 0;
    end else begin
      case(ECSU_state)
        ALL_CLEAR: begin
          if (thunderstorm == 1 | (wind > 15) | (visibility == 2'b11) | (temperature > 35) | (temperature < -35)) begin
            ECSU_state = HIGH_ALERT;
          end
          else if ((wind <= 15 & wind > 10) | (visibility == 2'b01)) begin
            ECSU_state = CAUTION;
          end
        end

        CAUTION: begin
          if (thunderstorm == 1| (wind > 15) | (visibility == 2'b11) | (temperature > 35) | (temperature < -35)) begin
            ECSU_state = HIGH_ALERT;
          end
          else if ((wind <= 10) & (visibility == 2'b00)) begin
            ECSU_state = ALL_CLEAR;
          end
        end

        HIGH_ALERT: begin
          if ((wind > 20 ) | (temperature > 40 ) | (temperature < -40)) begin
            ECSU_state = EMERGENCY;
          end
          else if ((thunderstorm == 0) & (wind <= 10) & (temperature >= -35 & temperature <= 35) & (visibility == 2'b01)) begin
            ECSU_state = CAUTION;
          end
        end
      endcase
    end
  end
always @(wind or visibility or temperature or thunderstorm) begin // Doesn't depends on the clock or any state it is purely asynchronous.(I'm working 231 using the assignment lol)
      case(ECSU_state)
        ALL_CLEAR: begin
          if (thunderstorm | (wind > 15) | (visibility == 2'b11) | (temperature > 35) | (temperature < -35)) begin
            severe_weather = 1;
          end
        end

        CAUTION: begin
          if (thunderstorm | (wind > 15) | (visibility == 2'b11) | (temperature > 35) | (temperature < -35)) begin
            severe_weather = 1;
          end   
        end

        HIGH_ALERT: begin
          if ((wind > 20 ) | (temperature > 40 ) | (temperature < -40)) begin
            emergency_landing_alert = 1;
          end
          else if ((thunderstorm == 0) & (wind <= 10) & (temperature >= -35 & temperature <= 35) & (visibility == 2'b01)) begin
            severe_weather = 0;
          end
        end
      endcase
    end

endmodule