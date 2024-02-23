`timescale 1ns/10ps
module half_adder_tb;
	reg A,B;
	wire S,C;

	half_adder UUT(A,B,S,C);
	initial begin
		#10 A = 0 ; B = 0; S = 0; C = 0;
		#10 A = 0 ; B = 1; S = 1; C = 0;
		#10 A = 1 ; B = 0; S = 1; C = 0;
		#10 A = 1 ; B = 1; S = 0; C = 1;
		#10 $finish;
	end
endmodule
