module machine_d(
    input wire x,
    input wire CLK,
    input wire RESET,
    output wire F,
    output wire [2:0] S
);
wire [11:0] A;

and g1(A[0],S[1],~x);
or DA1(A[1],S[2],A[0]);
dff DA(A[1],CLK,RESET,S[2]);

and g2(A[2],S[1],x);
and g3(A[3],~S[1],~x);
and g4(A[4],S[2],~x);
or g5(A[5],A[2],A[3]);
or g6(A[6],A[5],A[4]);

dff DB(A[6], CLK, RESET, S[1]);

and g7(A[7],S[0],~x);
and g8(A[8],~S[0],x);
or C(A[9],A[8],A[7]);
dff DC(A[9],CLK,RESET,S[0]);

and g9(F,~S[0],S[1],S[2]);
    
endmodule