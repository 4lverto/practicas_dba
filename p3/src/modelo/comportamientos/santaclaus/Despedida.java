/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.comportamientos.santaclaus;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import modelo.agentes.SantaClaus;

/**
 *
 * @author rafalpv
 */
public class Despedida extends OneShotBehaviour {

    SantaClaus santaClaus;

    public Despedida(SantaClaus santa) {
        this.santaClaus = santa;
    }

    @Override
    public void action() {

        ACLMessage mensajeDeDespedidaAgente = santaClaus.blockingReceive();

        if (mensajeDeDespedidaAgente.getPerformative() == ACLMessage.INFORM) {
            ACLMessage respuesta = mensajeDeDespedidaAgente.createReply(ACLMessage.INFORM);
            respuesta.setContent("!HoHoHo!");
            System.out.println("Se ha enviado despedida de Santa");
            santaClaus.send(respuesta);

        } else {
            System.out.println("Erro de Protocolo");
        }
        this.santaClaus.doDelete();

    }

}
