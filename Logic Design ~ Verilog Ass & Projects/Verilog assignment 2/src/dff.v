module dff (
    input D,      // Data input
    input CLK,    // Clock input
    input RESET,  // Asynchronous reset, active high
    output reg Q  // Output
);
    always @(posedge CLK or posedge RESET)
    begin
        if (RESET)
            Q <= 1'b0;  
        else
            Q <= D;     
    end
endmodule