`timescale 1ns/10ps

module full_adder(
    input A, B, Cin,
    output S, Cout
);
    wire Sum1, Carry1, Carry2;
    half_adder HA1(A,B,Sum1,Carry1);
    half_adder HA2(Cin,Sum1,S,Carry2);
    or(Cout,Carry1,Carry2);
endmodule
