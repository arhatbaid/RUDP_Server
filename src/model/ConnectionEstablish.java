package model;

public class ConnectionEstablish {
    private int client_id = 0;
    private int seq_no = 0;
    private int transmission_type = 0;
    private long retransmission_timeout = 0;

    public long getRetransmission_timeout() {
        return retransmission_timeout;
    }

    public void setRetransmission_timeout(long retransmission_timeout) {
        this.retransmission_timeout = retransmission_timeout;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }


    public int getSeq_no() {
        return seq_no;
    }

    public int getTransmission_type() {
        return transmission_type;
    }

    public void setSeq_no(int seq_no) {
        this.seq_no = seq_no;
    }

    public void setTransmission_type(int transmission_type) {
        this.transmission_type = transmission_type;
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
                .append("retransmission_timeout = ")
                .append(retransmission_timeout)
                .append("\n")
                .toString();
    }

    public byte[] toByte() {
        byte[] array = new byte[4];
        array[0] = (byte) client_id;
        array[1] = (byte) seq_no;
        array[2] = (byte) transmission_type;
        array[3] = (byte) retransmission_timeout;
        return array;
    }
}
