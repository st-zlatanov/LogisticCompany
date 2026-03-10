package com.logisticcompany.logistic_company.service;

import com.logisticcompany.logistic_company.model.Client;
import com.logisticcompany.logistic_company.repository.ClientRepository;
import com.logisticcompany.logistic_company.repository.ShipmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ShipmentRepository shipmentRepository;

    public ClientService(ClientRepository clientRepository, ShipmentRepository shipmentRepository) {
        this.clientRepository = clientRepository;
        this.shipmentRepository = shipmentRepository;
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    public void updateClient(Long id, Client updatedClient) {

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        client.setName(updatedClient.getName());
        client.setAddress(updatedClient.getAddress());
        client.setPhone(updatedClient.getPhone());

        clientRepository.save(client);
    }

    public boolean canDeleteClient(Client client){

        boolean hasSent = shipmentRepository.existsBySender(client);
        boolean hasReceived = shipmentRepository.existsByReceiver(client);

        return !(hasSent || hasReceived);
    }

    public void deleteOrDeactivateClient(Long id){

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        if(canDeleteClient(client)){
            clientRepository.delete(client);
        }else{
            client.getUser().setEnabled(false);
            clientRepository.save(client);
        }
    }

    public void activateClient(Long id){

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        client.getUser().setEnabled(true);

        clientRepository.save(client);
    }
}