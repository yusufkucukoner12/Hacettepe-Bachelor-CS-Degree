#include "Network.h"

Network::Network() {

}

void Network::process_commands(vector<Client> &clients, vector<string> &commands, int message_limit,
                      const string &sender_port, const string &receiver_port) {
    // TODO: Execute the commands given as a vector of strings while utilizing the remaining arguments.
    /* Don't use any static variables, assume this method will be called over and over during testing.
     Don't forget to update the necessary member variables after processing each command. For example,
     after the MESSAGE command, the outgoing queue of the sender must have the expected frames ready to send. */

    for(string command : commands){
        vector<string> splittedCommand = splitTheText(command, ' ');
        string command1 = "Command: " + command;
        std::cout << std::string(command1.size(), '-') << std::endl;
        std::cout << command1 << std::endl;
        std::cout << std::string(command1.size(), '-') << std::endl;



        if(splittedCommand[0] == "MESSAGE"){
            string senderId = splittedCommand[1];
            string receiverId = splittedCommand[2];
            Client* sender = returnClientById(senderId, clients);
            Client* receiver = returnClientById(receiverId, clients);

            string senderMac = sender->client_mac;
            string hopId = sender->routing_table[receiverId];
            Client* hop = returnClientById(hopId, clients);
            string receiverMac = hop->client_mac;

            string senderIp = sender->client_ip;
            string receiverIp = receiver->client_ip;

            vector<string> splittedMessage = splitTheText(command, '#');
            string message = splittedMessage[1];

            std::cout << "Message to be sent: " << '"' << message << '"' << std::endl << std::endl;
            int framenumber = 1;
            for (int i = 0; i < message.length(); i+=message_limit) // Creating a frame for each chunk of message which consist of 4 packets.
            {  
                stack<Packet*> packets;
                string chunkMessage = message.substr(i, message_limit);
                ApplicationLayerPacket* applicationLayer = new ApplicationLayerPacket(0,senderId,receiverId,chunkMessage);
                TransportLayerPacket* transportLayer = new TransportLayerPacket(1,sender_port,receiver_port);
                NetworkLayerPacket* networkLayer = new NetworkLayerPacket(2,senderIp,receiverIp);
                PhysicalLayerPacket* physicalLayer = new PhysicalLayerPacket(3,senderMac,receiverMac);
                physicalLayer->frame_number = framenumber;
                packets.push(applicationLayer); packets.push(transportLayer); packets.push(networkLayer); packets.push(physicalLayer);   
                sender->outgoing_queue.push(packets);
                
                std::cout << "Frame #" << framenumber << "\n";
                physicalLayer->print();
                networkLayer->print();
                transportLayer->print();
                applicationLayer->print();
                std::cout << "Message chunk carried: " << '"' << chunkMessage << '"' << "\n";
                std::cout << "Number of hops so far: " << physicalLayer->number_hops << "\n";
                framenumber++;

                std::cout << "--------" << std::endl;
            }
        }
        else if (splittedCommand[0] == "SEND"){
            // The thing here is sending the message from the outcoming of the client to the incoming of the other client.
            // Do it for all clients for each one find the macaddress from the top of the stack and find the messages to send.
            // PhysicalLayerPacket* physicalPacket = dynamic_cast<PhysicalLayerPacket*>(clients[x].outgoing_queue.front().top());
            for(int i = 0 ; i < clients.size() ; i++){
                string fullMessage;
                int a = 0;
                while(!clients[i].outgoing_queue.empty()){
                    ActivityType activity;
                    Client* nextClient;
                    Client* senderClient;
                    
                    stack<Packet*> frame = clients[i].outgoing_queue.front();
                    PhysicalLayerPacket* physicalPacket = dynamic_cast<PhysicalLayerPacket*>(frame.top());
                    frame.pop();
                    NetworkLayerPacket* networkPacket = dynamic_cast<NetworkLayerPacket*>(frame.top());
                    frame.pop();
                    TransportLayerPacket* transportPacket = dynamic_cast<TransportLayerPacket*>(frame.top());
                    frame.pop();
                    ApplicationLayerPacket* applicationLayer = dynamic_cast<ApplicationLayerPacket*>(frame.top());
                    
                    nextClient = returnClientyByMacAddress(physicalPacket->receiver_MAC_address, clients);
                    senderClient = returnClientyByMacAddress(physicalPacket->sender_MAC_address,clients);

                    physicalPacket->number_hops++;
                    

                    
                    
                    clients[i].outgoing_queue.front().pop();
                    clients[i].outgoing_queue.front().push(physicalPacket);

                    nextClient->incoming_queue.push(clients[i].outgoing_queue.front());
                    senderClient->outgoing_queue.pop();
                    

                    std::cout <<  "Client " << senderClient->client_id << " sending frame #" << physicalPacket->frame_number << " to client " << nextClient->client_id << std::endl;
                    physicalPacket->print();
                    networkPacket->print();
                    transportPacket->print();
                    applicationLayer->print();
                    std::cout << "Message chunk carried: " << '"' << applicationLayer->message_data << '"' << "\n";
                    std::cout << "Number of hops so far: " << physicalPacket->number_hops << "\n";
                    

                    std::cout << "--------" << std::endl;

                    string message = applicationLayer->message_data;
                    fullMessage = fullMessage + message;

                    for(char dot : message){
                        if((dot == '.' || dot == '?' || dot == '!') && (applicationLayer->sender_ID == senderClient->client_id)){
                            activity = ActivityType::MESSAGE_SENT;
                            string time = returnCurrentTimeAsString();
                            Log newLog(time,fullMessage,physicalPacket->frame_number,physicalPacket->number_hops-1,applicationLayer->sender_ID,applicationLayer->receiver_ID,true,activity);
                            senderClient->log_entries.push_back(newLog);
                            fullMessage = "";
                        }
                    }
                }
            }
            
        }
        else if (splittedCommand[0] == "RECEIVE"){
            for(int i = 0 ; i < clients.size() ; i++){
                bool deneme = true;
                string fullMessage;
                while(!clients[i].incoming_queue.empty()){
                    ActivityType activityType;
                    Client* client;
                    Client* newReceiver;
                    Client* sender;
                    stack<Packet*> frame = clients[i].incoming_queue.front();
                    PhysicalLayerPacket* physicalPacket = dynamic_cast<PhysicalLayerPacket*>(frame.top());
                    frame.pop();
                    NetworkLayerPacket* networkPacket = dynamic_cast<NetworkLayerPacket*>(frame.top());
                    frame.pop();
                    TransportLayerPacket* transportPacket = dynamic_cast<TransportLayerPacket*>(frame.top());
                    frame.pop();
                    ApplicationLayerPacket* applicationLayer = dynamic_cast<ApplicationLayerPacket*>(frame.top());

                    string message_chunk = applicationLayer->message_data;
                    fullMessage = fullMessage + message_chunk;

                    client = returnClientyByMacAddress(physicalPacket->receiver_MAC_address, clients); // This is the current hop client
                    sender = returnClientyByMacAddress(physicalPacket->sender_MAC_address, clients);
                    string newReceiverId = client->routing_table[applicationLayer->receiver_ID];
                    int frameNumber = physicalPacket->frame_number;
                    int numberHops = physicalPacket->number_hops;
                    string senderId = applicationLayer->sender_ID;
                    string receiverId = applicationLayer->receiver_ID;
                    


                    if(!isClient(newReceiverId,clients)){
                        activityType = ActivityType::MESSAGE_DROPPED;

                        std::cout << "Client " << client->client_id << " receiving frame #" << physicalPacket->frame_number << " from client " <<  sender->client_id << ", but intended for client " << applicationLayer->receiver_ID << ". Forwarding... " << "\n";
                        std::cout << "Error: Unreachable destination. Packets are dropped after " << physicalPacket->number_hops << " hops!" << std::endl;
                        stack<Packet*> tmpPack =client->incoming_queue.front();
                        delete tmpPack.top();
                        tmpPack.pop();
                        delete tmpPack.top();
                        tmpPack.pop();
                        delete tmpPack.top();
                        tmpPack.pop();
                        delete tmpPack.top();
            
                        client->incoming_queue.pop();
                    }
                    else if(client->client_id == applicationLayer->receiver_ID){
                        
                        newReceiver = returnClientById(newReceiverId,clients);

                        activityType = ActivityType::MESSAGE_RECEIVED;
                        std::cout << "Client "<< client->client_id << " receiving frame #" << physicalPacket->frame_number  << " from client " << sender->client_id << ", originating from client " << applicationLayer->sender_ID << "\n";

                        physicalPacket->print();
                        networkPacket->print();
                        transportPacket->print();
                        applicationLayer->print();
                        
                        std::cout << "Message chunk carried: " << '"' << applicationLayer->message_data << '"' << "\n";

                        std::cout << "Number of hops so far: " << physicalPacket->number_hops << "\n";
                        std::cout << "--------" << std::endl;
                        
                        
                        physicalPacket->sender_MAC_address = client->client_mac;
                        stack<Packet*> tmpPack =client->incoming_queue.front();
                        delete tmpPack.top();
                        tmpPack.pop();
                        delete tmpPack.top();
                        tmpPack.pop();
                        delete tmpPack.top();
                        tmpPack.pop();
                        delete tmpPack.top();
                        client->incoming_queue.pop();
                    }
                    else{
                        physicalPacket->sender_MAC_address = client->client_mac;
                        newReceiver = returnClientById(newReceiverId,clients);
                        if(deneme){
                            std::cout << "Client " << client->client_id << " receiving a message from client " <<  sender->client_id << ", but intended for client " << receiverId << ". Forwarding... " << "\n";
                            activityType = ActivityType::MESSAGE_FORWARDED;
                            deneme = false;
                        }
                        
                        std::cout << "Frame #" << physicalPacket->frame_number <<  " MAC address change: New sender MAC "<< client->client_mac << ", new receiver MAC " << newReceiver->client_mac << std::endl;
                        physicalPacket->receiver_MAC_address = newReceiver->client_mac;

                        client->incoming_queue.front().pop();
                        client->incoming_queue.front().push(physicalPacket);

                        client->outgoing_queue.push(clients[i].incoming_queue.front());
                        client->incoming_queue.pop();
                    }
                    for(char dot : message_chunk){
                        if((dot == '.' || dot == '?' || dot == '!')){
                            if(activityType == ActivityType::MESSAGE_RECEIVED){
                                std::cout << "Client " << client->client_id << " received the message " << '"' << fullMessage << '"' << " from client " << senderId << "." << std::endl; 
                                
                            }
                            std::cout << "--------" << std::endl;
                            bool success = false;
                            string time = returnCurrentTimeAsString();
                            if(activityType != ActivityType::MESSAGE_DROPPED){
                                success = true;
                            }
                            Log newLog(time,fullMessage,frameNumber,numberHops,senderId,receiverId,success,activityType);
                            client->log_entries.push_back(newLog);
                            fullMessage = "";
                            deneme = true;
                        }                 
                    }
                }
            }        
        }  
        else if (splittedCommand[0] == "PRINT_LOG")
        {
            string clientId = splittedCommand[1];
            Client* client = returnClientById(clientId,clients);
            if(client->log_entries.size() > 0){
                std::cout << "Client " << clientId << " Logs: " << "\n";
                for(int i = 0 ; i<client->log_entries.size() ; i++){
                    std::cout << "--------------" << std::endl;
                    std::cout << "Log Entry #" << i+1 << ":" <<std::endl;
                    std::cout << "Activity: " << activityToString(client->log_entries[i].activity_type)<<"\n";
                    std::cout << "Timestamp: " << client->log_entries[i].timestamp << std::endl;
                    std::cout << "Number of frames: " << client->log_entries[i].number_of_frames << std::endl;
                    std::cout << "Number of hops: " << client->log_entries[i].number_of_hops << std::endl;
                    std::cout << "Sender ID: " << client->log_entries[i].sender_id << std::endl;
                    std::cout << "Receiver ID: " << client->log_entries[i].receiver_id << std::endl;
                    std::cout << (client->log_entries[i].success_status ? "Success: Yes" : "Success: No") << std::endl;
                    if(client->log_entries[i].activity_type == ActivityType::MESSAGE_RECEIVED || client->log_entries[i].activity_type == ActivityType::MESSAGE_SENT){
                        std::cout << "Message: " << '"' << client->log_entries[i].message_content << '"' << std::endl;
                    }
                }
            }
        }
        else if(splittedCommand[0] == "SHOW_FRAME_INFO"){
            string clientId = splittedCommand[1];
            string outOrIn = splittedCommand[2];
            int frameId = stoi(splittedCommand[3]);
            string queueName;
            Client* client = returnClientById(clientId, clients);
            queue<stack<Packet*>> queue;
            if(outOrIn == "out"){
                queue = client->outgoing_queue;
                queueName = "outgoing";
            }
            else{
                queue = client->incoming_queue;
                queueName = "incoming";
            }
            if(queue.size() < frameId){
                std::cout << "No such frame." << std::endl;
            }
            else{
                for(int i = 1 ; i < frameId ; i++){
                    queue.pop();
                }
                stack<Packet*> frame = queue.front();
                PhysicalLayerPacket* physicalPacket = dynamic_cast<PhysicalLayerPacket*>(frame.top());
                frame.pop();
                NetworkLayerPacket* networkPacket = dynamic_cast<NetworkLayerPacket*>(frame.top());
                frame.pop();
                TransportLayerPacket* transportPacket = dynamic_cast<TransportLayerPacket*>(frame.top());
                frame.pop();
                ApplicationLayerPacket* applicationLayer = dynamic_cast<ApplicationLayerPacket*>(frame.top());  

                std::cout << "Current Frame #"<< frameId <<  " on the " << queueName << " queue of client " <<  clientId << "\n";      
                std::cout << "Carried Message: " << '"' << applicationLayer->message_data << '"' << std::endl;
                std::cout << "Layer 0 info: ";
                applicationLayer->print();
                std::cout << "Layer 1 info: ";
                transportPacket->print();
                std::cout << "Layer 2 info: ";
                networkPacket->print();
                std::cout << "Layer 3 info: ";
                physicalPacket->print();
                std::cout << "Number of hops so far: " << physicalPacket->number_hops << std::endl;
            }
            
        }
        else if (splittedCommand[0] == "SHOW_Q_INFO")
        {
            string clientId = splittedCommand[1];
            string outOrIn = splittedCommand[2];
            Client* client = returnClientById(clientId, clients);
            queue<stack<Packet*>> queue;
            if(outOrIn == "out"){
                queue = client->outgoing_queue;
                std::cout << "Client " << clientId << " Outgoing Queue Status" << std::endl;
                std::cout << "Current total number of frames: " << queue.size() << "\n";
            }
            else{
                queue = client->incoming_queue;
                std::cout << "Client " << clientId  << " Incoming Queue Status" << std::endl;
                std::cout << "Current total number of frames: " << queue.size() << "\n";
            }
        }
        else{
            std::cout << "Invalid command." << std::endl;
        }
        
        
    }   
}

vector<Client> Network::read_clients(const string &filename) {
    vector<Client> clients;
    ifstream file(filename);
    if (!file.is_open()) {
        cerr << "Error opening file: " << filename << endl;

    }
    std::string line;
    
    while(getline(file,line)){
        vector<string> splittedLine = splitTheText(line, ' ');
        if(splittedLine.size() > 1){
            Client newClient(splittedLine[0], splittedLine[1], splittedLine[2]); 
            clients.push_back(newClient);
        }
    }
    return clients;
}

void Network::read_routing_tables(vector<Client> &clients, const string &filename) {
    // TODO: Read the routing tables from the given input file and populate the clients' routing_table member variable.
    ifstream file(filename);
    if (!file.is_open()) {
        cerr << "Error opening file: " << filename << endl;

    }
    string line;    
    int index = 0;
    while(getline(file,line)){
        if(line == "-"){
            index++;
        }
        else{
            vector<string> splittedText = splitTheText(line, ' ');
            clients[index].routing_table[splittedText[0]] = splittedText[1];
        }
    }
}

// Returns a list of token lists for each command
vector<string> Network::read_commands(const string &filename) {
    // TODO: Read commands from the given input file and return them as a vector of strings.

    vector<string> commands;
    ifstream file(filename);
    if (!file.is_open()) {
        cerr << "Error opening file: " << filename << endl;

    }
    string line;    
    getline(file,line);
    while(getline(file,line)){
        commands.push_back(line);
    }
    return commands;
}

Network::~Network() {
    // TODO: Free any dynamically allocated memory if necessary.
}
vector<string> Network::splitTheText(string inputString,char discriminator){

    std::istringstream iss(inputString);
    std::vector<std::string> tokens; 
    std::string token;
    while (std::getline(iss, token, discriminator)) {
        tokens.push_back(token); 
    }
    return tokens;
}
Client* Network::returnClientById(string id, vector<Client> &clients){
    for(int i = 0 ; i < clients.size() ; i++){
        if(clients[i].client_id == id){
            return &clients[i];
        }
    }
}

Client* Network::returnClientyByMacAddress(string macAddress, vector<Client> &clients){
    for(int i = 0 ; i < clients.size() ; i++){
        if(clients[i].client_mac == macAddress){
            return &clients[i];
        }
    }
}
std::string Network::activityToString(ActivityType activity) {
    switch (activity) {
        case ActivityType::MESSAGE_RECEIVED:
            return "Message Received";
        case ActivityType::MESSAGE_FORWARDED:
            return "Message Forwarded";
        case ActivityType::MESSAGE_SENT:
            return "Message Sent";
        case ActivityType::MESSAGE_DROPPED:
            return "Message Dropped";
        default:
            return "UNKNOWN";
    }
}
std::string Network::returnCurrentTimeAsString(){
    auto currentTime = std::chrono::system_clock::now();


    std::time_t currentTime_t = std::chrono::system_clock::to_time_t(currentTime);

    std::tm* timeInfo = std::localtime(&currentTime_t);

    std::stringstream ss;
    ss << std::put_time(timeInfo, "%Y-%m-%d %H:%M:%S");

    return ss.str();
}
bool Network::isClient(string id, vector<Client> &clients){
    if(id == ""){
        return true;
    }
    for(int i = 0; i<clients.size() ; i++){
        if(clients[i].client_id == id){
            return true;
        } 
    }
    return false;

}

