`timescale 1ns / 1ps

module machine_d_tb;
    reg x;
    reg CLK;
    reg RESET;

    wire F;
    wire [2:0] S;
    wire [11:0] A;

    machine_d uut (
        .x(x),
        .CLK(CLK),
        .RESET(RESET),
        .F(F),
        .S(S),
        .A(A)
    );

    always begin
        #5 CLK = ~CLK;
    end

    initial begin
        CLK = 0;
        RESET = 1;
        x = 0;
        
        #10 RESET = 0;


        repeat (8) begin
            #5 x = ~x;
            repeat (2) begin
                #5 CLK = ~CLK;
                repeat (2) begin
                    #5 RESET = ~RESET;

                    // Display output values
                    $display("Time=%0t x=%b CLK=%b RESET=%b F=%b S=%b A=%b", $time, x, CLK, RESET, F, S, A);
                end
            end
        end

        #10 $finish;
    end
endmodule