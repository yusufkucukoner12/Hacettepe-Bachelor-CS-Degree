module jkff (
    input J,      // Data input
    input K,      // Data input
    input CLK,    // Clock input
    input RESET,  // Asynchronous reset, active high
    output reg Q  // Output
);
    always @(posedge CLK)
    begin
        if (RESET)
            Q <= 1'b0;  
        else
            if (J & ~K)    
                Q <= ~Q;
            else if (J)     
                Q <= 1'b1;
            else if (K)     
                Q <= 1'b0;
    end
endmodule