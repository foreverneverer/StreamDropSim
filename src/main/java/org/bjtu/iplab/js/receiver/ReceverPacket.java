package org.bjtu.iplab.js.receiver;

import org.bjtu.iplab.js.StreamInfo.PacketInfo;

public class ReceverPacket extends PacketInfo{

    public transient byte[] sps_pps_nalu_bytes = new byte[37];

    public ReceverPacket(byte[] recv_packet_info_bytes) {
        this.packet_info_bytes = recv_packet_info_bytes;

        this.header_bytes = new byte[4];
        this.sps_nalu_bytes = new byte[28];
        this.pps_nalu_bytes = new byte[9];
        this.general_nalu_bytes = new byte[recv_packet_info_bytes.length - 41];

        System.arraycopy(packet_info_bytes,0,header_bytes,0,4);
        System.arraycopy(packet_info_bytes, 4, sps_nalu_bytes,0,28);
        System.arraycopy(packet_info_bytes, 32, pps_nalu_bytes,0,9);
        System.arraycopy(packet_info_bytes, 4, sps_pps_nalu_bytes,0,37);
        System.arraycopy(packet_info_bytes,41,general_nalu_bytes,0,general_nalu_bytes.length);

        this.gop_num = unsigned_int(header_bytes[1]) + 256 * unsigned_int(header_bytes[0]);
        this.packet_num = unsigned_int(header_bytes[3]) + 256 * unsigned_int(header_bytes[2]);
    }

    private static int unsigned_int(int nb) {
        if (nb >= 0)
            return (nb);
        else
            return (256 + nb);
    }
}
