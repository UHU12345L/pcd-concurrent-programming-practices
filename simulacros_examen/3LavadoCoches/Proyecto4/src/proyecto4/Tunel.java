package proyecto4;

/**
 *
 * @author Laura
 */
public class Tunel {

    //3 puestos. Maximo 2 coches. Maximo 2 furgos, o 3 si no hay coches esperando.
    //Si pueden entrar de los dos tipos, prioridad para las furgos.
    int puestos = 3;          //puestos libres
    int cochesDentro = 0;     //coches lavandose (max 2)
    int furgosDentro = 0;     //furgos lavandose (max 2, o 3 si no hay coches esperando)
    int cochesEsperando = 0;  //lo miran las furgos: decide si su maximo es 2 o 3
    int furgosEsperando = 0;  //lo miran los coches: para la prioridad de las furgos
    private MiCanvas cv;

    public Tunel(MiCanvas cv) {
        this.cv = cv;
        
    }

    public synchronized void entraCoche(int id) throws InterruptedException {
        cv.mete(cv.colaCoche, id, 'C');
        cochesEsperando++;   //antes del while: aviso de que hay un coche esperando
        //espero si no hay puesto, si ya hay 2 coches,
        //o si hay furgos esperando y alguna de ellas puede entrar (prioridad).
        //Mientras yo espero, el maximo de furgos es 2, por eso miro furgosDentro < 2
        while (puestos == 0 || cochesDentro == 2 || (furgosEsperando > 0 && furgosDentro < 2)) {
            wait();
        }
        cv.mete(cv.colaTunel, id, 'C');
        cv.saca(cv.colaCoche, id);
        cochesEsperando--;   //despues del while: ya no espero
        puestos--;
        cochesDentro++;
        System.out.println("Coche " + id + " entra, quedan " + puestos + " puestos libres");
    }
    
    public synchronized void saleCoche(int id) {
        cv.saca(cv.colaTunel, id);
        puestos++;
        cochesDentro--;
        System.out.println("Coche " + id + " sale, quedan " + puestos + " puestos libres");
        notifyAll();
    }
    
    public synchronized void entraFurgo(int id) throws InterruptedException {
        cv.mete(cv.colaFurgo, id, 'F');
        furgosEsperando++;
        //espero si no hay puesto, si ya hay 3 furgos,
        //o si ya hay 2 furgos y hay algun coche esperando (entonces el maximo es 2)
        while (puestos == 0 || furgosDentro == 3 || (furgosDentro == 2 && cochesEsperando > 0)) {
            wait();
        }
        cv.mete(cv.colaTunel, id, 'F');
        cv.saca(cv.colaFurgo, id);
        furgosEsperando--;
        puestos--;
        furgosDentro++;
        System.out.println("Furgo " + id + " entra, quedan " + puestos + " puestos libres");
    }
    
    public synchronized void saleFurgo(int id) {
        cv.saca(cv.colaTunel, id);
        puestos++;
        furgosDentro--;
        System.out.println("Furgo " + id + " sale, quedan " + puestos + " puestos libres");
        notifyAll();
    }
}
