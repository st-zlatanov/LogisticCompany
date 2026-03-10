package com.logisticcompany.logistic_company.service;

import com.logisticcompany.logistic_company.model.Client;
import com.logisticcompany.logistic_company.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
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
}