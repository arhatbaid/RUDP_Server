package model;

import java.io.Serializable;

public class DataTransfer implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private int client_id = 0;
    private int seq_no = 0;
    private final int transmission_type = 3;
    private int is_last_packet = 0;

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getSeq_no() {
        return seq_no;
    }

    public void setSeq_no(int seq_no) {
        this.seq_no = seq_no;
    }

    public int getIs_last_packet() {
        return is_last_packet;
    }

    public void setIs_last_packet(int is_last_packet) {
        this.is_last_packet = is_last_packet;
    }

    public int getTransmission_type() {
        return transmission_type;
    }

    @Override
    public String toString() {
        return new StringBuffer("client_id = ")
                .append(client_id)
                .append("\n")
                .append("seq_no = ")
                .append(seq_no)
                .append("\n")
                .append("transmission_type = ")
                .append(transmission_type)
                .append("\n")
                .append("is_last_packet = ")
                .append(is_last_packet)
                .append("\n")
                .toString();
    }

    public byte[] toByte() {
        byte[] array = new byte[4];
        array[0] = (byte) client_id;
        array[1] = (byte) seq_no;
        array[2] = (byte) transmission_type;
        array[3] = (byte) is_last_packet;
        return array;
    }
}
