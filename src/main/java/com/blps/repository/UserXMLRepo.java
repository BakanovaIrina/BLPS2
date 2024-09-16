package com.blps.repository;

import com.blps.entity.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserXMLRepo {

    private final String crutchPath = "./crutch";

    public UserXMLRepo() {
        File crutchDir = new File(crutchPath);
        if (!crutchDir.exists()) {
            crutchDir.mkdirs();
        }
    }

    private File fileFactory(User user) {
        return new File(crutchPath + "/" + user.getUsername() + ".xml");
    }

    public void save(User user) {
        File file = fileFactory(user);
        try (XMLEncoder encoder = new XMLEncoder(new FileOutputStream(file))) {
            encoder.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User findByUsername(String username) {
        try {
            File userFile = Arrays.stream(new File(crutchPath).listFiles())
                    .filter(f -> f.getName().contains(username))
                    .findFirst()
                    .orElseThrow(() -> new EmptyResultDataAccessException(1));
            try (XMLDecoder decoder = new XMLDecoder(new FileInputStream(userFile))) {
                Object o = decoder.readObject();
                return (User) o;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List<User> findAll() {
        File crutchDir = new File(crutchPath);
        File[] files = crutchDir.listFiles();
        if (files == null) {
            return List.of();
        }

        return Arrays.stream(files)
                .map(file -> {
                    try (XMLDecoder decoder = new XMLDecoder(new FileInputStream(file))) {
                        return (User) decoder.readObject();
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public void delete(User user) {
        fileFactory(user).delete();
    }
}

