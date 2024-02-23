`timescale 1ns/10ps

module multiplier_tb;

  reg [2:0] A, B;
  wire [5:0] P;

  multiplier uut(.A(A), .B(B), .P(P));

  initial begin

    integer i, j;
    for (i = 0; i < 8; i = i + 1) begin
      A = i;
      for (j = 0; j < 8; j = j + 1) begin
        B = j;
        #5;  
      end
    end

    $finish;
  end

endmodule