package com.cpr.doantotnghiep.model;

import com.cpr.doantotnghiep.utils.KindRoom;

public class Room {
    private String room;
    private int image;
    private KindRoom kindRoom;

    public void setKindRoom(KindRoom kindRoom){
        this.kindRoom = kindRoom;
    }

    public KindRoom getKindRoom(){
        return kindRoom;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Room(String room, int image, KindRoom kindRoom) {
        this.room = room;
        this.image = image;
        this.kindRoom = kindRoom;
    }
}
