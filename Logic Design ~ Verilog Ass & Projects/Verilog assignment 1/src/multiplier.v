`timescale 1ns/10ps

module multiplier (
    input [2:0] A, B,
    output [5:0] P
);
    wire P0,P1,P2,P3,P4,P5  ;
    wire arakablo, arakablo1, arakablo2, arakablo3, arakablo4, arakablo5, arakablo6, arakablo7, arakablo8, arakablo9, arakablo10, arakablo11, arakablo12, arakablo13, arakablo14;

    and g0(P0,A[0],B[0]);
    and g1(arakablo,A[1],B[0]);
    and g2(arakablo1,A[0],B[1]);

    half_adder HA1(arakablo,arakablo1,P1,arakablo2); // arakablo 2: C

    and g3(arakablo3,A[2],B[0]);
    and g4(arakablo4,A[1],B[1]);

    half_adder HA2(arakablo3, arakablo4, arakablo5, arakablo6); // arakablo 5: B // arakablo6: Cin


    and g5(arakablo7,A[0],B[2]); //A 

    full_adder FA1(arakablo7,arakablo5,arakablo2, P2, arakablo8); // arakablo8: Cout

    and g6(arakablo9, A[2], B[1]);
    and g7(arakablo10, A[1], B[2]);

    full_adder FA2 (arakablo9, arakablo10, arakablo6,arakablo11,arakablo12); 

    half_adder HA3(arakablo11,arakablo8,P3,arakablo13);

    and g8(arakablo14,A[2],B[2]);

    full_adder FA3(arakablo14, arakablo12, arakablo13, P4 ,P5);

    assign P[0] = P0;
    assign P[1] = P1;
    assign P[2] = P2;
    assign P[3] = P3;
    assign P[4] = P4;
    assign P[5] = P5;


endmodule
