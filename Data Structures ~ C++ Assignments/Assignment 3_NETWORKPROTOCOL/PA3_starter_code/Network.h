#ifndef NETWORK_H
#define NETWORK_H

#include <vector>
#include <iostream>
#include "Packet.h"
#include "Client.h"

using namespace std;

class Network {
public:
    Network();
    ~Network();

    // Executes commands given as a vector of strings while utilizing the remaining arguments.
    void process_commands(vector<Client> &clients, vector<string> &commands, int message_limit, const string &sender_port,
                     const string &receiver_port);

    // Initialize the network from the input files.
    vector<Client> read_clients(string const &filename);
    void read_routing_tables(vector<Client> & clients, string const &filename);
    vector<string> read_commands(const string &filename); 
    vector<string> splitTheText(string inputText,char discriminator);
    Client* returnClientById(string id, vector<Client> &clients);
    Client* returnClientyByMacAddress(string macAddress, vector<Client> &clients);
    std::string activityToString(ActivityType activity);
    std::string returnCurrentTimeAsString();
    bool isClient(string id, vector<Client> &clients);
    
};

#endif  // NETWORK_H
