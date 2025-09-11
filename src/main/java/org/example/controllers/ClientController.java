package org.example.controllers;

import org.example.models.Client;
import org.example.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @GetMapping
    public List<Client> getAllClients() {
        return clientService.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Integer id) {
        Client client = clientService.findById(id);
        return ResponseEntity.ok(client);
    }


    @PostMapping
    public Client createClient(@RequestBody Client client) {
        clientService.save(client);
        return client;
    }


    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Integer id, @RequestBody Client client) {
        client.setId(id);
        Client updated = clientService.update(client);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Integer id) {
        Client client = clientService.findById(id);
        clientService.delete(client);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/search")
    public List<Client> searchClientsByName(@RequestParam String name) {
        return clientService.findByName(name);
    }
}
