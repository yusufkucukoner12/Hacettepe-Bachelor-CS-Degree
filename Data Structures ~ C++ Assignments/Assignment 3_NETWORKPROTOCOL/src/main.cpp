#include <iostream>
#include "Network.h"

using namespace std;

int main(int argc, char *argv[]) {

    // Instantiate HUBBMNET
    Network* HUBBMNET = new Network();

    // Read from input files
    vector<Client> clients = HUBBMNET->read_clients(argv[1]);
    HUBBMNET->read_routing_tables(clients, argv[2]);
    vector<string> commands = HUBBMNET->read_commands(argv[3]);

    // Get additional parameters from the cmd arguments
    int message_limit = stoi(argv[4]);
    string sender_port = argv[5];
    string receiver_port = argv[6];

    // Run the commands
    HUBBMNET->process_commands(clients, commands, message_limit, sender_port, receiver_port);

    // Delete HUBBMNET
    
    delete HUBBMNET;

    return 0;
}


