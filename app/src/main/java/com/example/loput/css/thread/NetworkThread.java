package com.example.loput.css.thread;

import android.net.Network;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Xeno on 2016-11-10.
 */

public class NetworkThread extends Thread {

    private ArrayList<byte[]> queue;
    private Socket s;
    private OutputStream os;

    private boolean stopFlag = false;

    public NetworkThread()
    {
        super();
        queue = new ArrayList<>();
    }

    public void addToQueue(byte[] data )
    {
        synchronized (queue) {
            queue.add( data );
        }
    }

    public void stopThread()
    {
        stopFlag = true;
    }

    private void write( byte[] msg ) {
        try {
            os.write(msg);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void run() {
        super.run();

        try
        {
            s = new Socket();
            s.connect(new InetSocketAddress("127.0.0.1", 8327));

            os = s.getOutputStream();

        }
        catch( Exception e ) {
            e.printStackTrace();
        }

        //write( "testmsg".getBytes());

        while( !stopFlag ) {
            synchronized ( queue )
            {
                if( queue.size() > 0 ) {
                    write(queue.get(0));
                    queue.remove(0);
                }
            }

        }

        try
        {
            os.flush();
            s.close();
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }
}
