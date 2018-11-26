package ar.edu.itba.paw.homehelper.dto;

import ar.edu.itba.paw.model.User;

public class ClientDto {
    private int id;

    public ClientDto(User client) {
        this.id = client.getId();
    }

    public int getId() {
        return id;
    }
}
