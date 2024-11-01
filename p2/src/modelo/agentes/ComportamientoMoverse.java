/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.agentes;

import jade.core.behaviours.Behaviour;

class ComportamientoMoverse extends Behaviour {
    @Override
    public void action() {
        // Lógica para moverse en el entorno
        System.out.println("El agente se está moviendo...");
    }

    @Override
    public boolean done() {
        // Aquí defines cuándo el comportamiento debería detenerse
        return false;
    }
}
