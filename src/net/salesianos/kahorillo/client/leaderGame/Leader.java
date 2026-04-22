package net.salesianos.kahorillo.client.leaderGame;

import net.salesianos.kahorillo.client.emitter.ClientEmitter;
import net.salesianos.kahorillo.client.listener.ServerListener;

public class Leader {

  ServerListener serverListener;
  ClientEmitter clientEmitter;

  public Leader (ServerListener serverListener, ClientEmitter clientEmitter) {

    this.serverListener = serverListener;
    this.clientEmitter = clientEmitter;

  }
  
  public void createQuestions() {
    
  }

}
