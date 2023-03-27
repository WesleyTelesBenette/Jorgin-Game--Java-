package Principal;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener {

    //Variáveis gerais
    public static JFrame frame;
    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static int WIDTH = (int) screenSize.getWidth();
    private static int HEIGHT = (int) screenSize.getHeight();
    public static int SCALE = 5;
    private Thread thread;
    private Boolean isRunning = true;
    private BufferedImage image;

    //Objetos
    public static World word;
    public static SpriteSheet sprite_tile;
    Player player;
    public static List<Entity> entities;

    //Método construtor (vai rodar uma única vez, quando for criado como objeto)
    public Game() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT)); //Define dimensões para o Canvas
        createFrame(); //Cria e cofigura a janela

        //Inicializar objetos-ferramentas
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB); //Cria um Buffer para renderizar imagens
        sprite_tile = new SpriteSheet(); //Adiciona a folha de sprites
        addKeyListener(this); //Adiciona o pressionamento de teclas
        
        //Inicializar objetos-elementos
        player = new Player(30*SCALE, 96*SCALE, 9, 12, SpriteSheet.getSprite(SpriteSheet.spriteSheet, 9, 0, 9, 12)); //Cria um jogador
        entities = new ArrayList<Entity>(); //Cria uma lista para armazenar todas as entidades
        entities.add(player); //Adiciona o jogador na lista de entidades
        word = new World(0);
    }

    //Cria e configura a janela
    public void createFrame() {
        frame = new JFrame("Nome da Janela"); //Cria uma Janela e da o nome dela
        frame.setUndecorated(true); //Deixa o jogo em FULLSCREEN
        frame.add(this); //Adiciona as dimensões e propriedades do canvas
        frame.setResizable(false); //Trava o tamanho da janela
        frame.pack(); //Empacota o Canvas com a janela, para calcular o tamanho dela
        frame.setLocationRelativeTo(null); //O janela vai ser criada no centro da tela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Quando o jogo fechar, o program tem que parar de rodar
        frame.setVisible(true); //Deixa a janela visivel
    }

    //Inicia o loop do jogo
    public synchronized void start() {
        thread = new Thread(this); //Cria uma nova tarefa
        isRunning = true; //O loop do jogo está ativado
        thread.start();
    }

    //Garante que o loop vai parar
    public synchronized void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Game game = new Game(); //Cria o jogo
        game.start(); //Inicia o jogo
    }

    //Lógica do Jogo (funcionalidades)
    public void tick() {
        //Roda os códigos dentro de todas as entidades
        for (int c = 0; c < entities.size(); c++) {
            Entity e = entities.get(c);
            e.tick();
        }
    }

    //Gráficos do Jogo
    public void render() {
        BufferStrategy bs = this.getBufferStrategy(); //Buffers de ótimização de renderização
        if (bs == null) { //Se ele não existir, cria um e vaza
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = image.getGraphics(); //Objetos para renderizar coisas na tela
        g.setColor(new Color(16, 16, 16)); //Define a cor do fundo
        g.fillRect(0, 0, WIDTH, HEIGHT); //Denha um Fundo
        Graphics2D g2 = (Graphics2D) g; //Adiciona todos os métodos de Graphics2D

        /* RENDERIZAÇÃO OBJETOS */

        //Desenha tudo dentro de todas as entidades
        word.render(g);
        for (int c = 0; c < entities.size(); c++) {
            Entity e = entities.get(c);
            e.render(g);
        }

        /************************/

        //Desenha na tela de fato
        g.dispose(); //Método de otimização (limpa dados inúteis das imagens)
        g = bs.getDrawGraphics();  //Usa o Buffer de otimização
        g.drawImage(image, 0, 0, WIDTH, HEIGHT, null); //Desenha tudo que foi definido nas linhas a cima
        bs.show(); //Mostrar tudo que for desenhado
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime(); //Tempo de referência super preciso do computador
        double framesPerSecond = 60.0; //Frames do jogo
        double nanoSec = 1000000000 / framesPerSecond; //Tempo de referencia para cada frame
        double delta = 0; //Variável de controle de frame
        double fps = 0; //Frames, só pra debugar a performance
        double fpsTimer = System.currentTimeMillis(); //Tempo menos preciso do computador
        while (isRunning) {
            long now = System.nanoTime(); //Tempo atual super preciso do computador
            delta += (now - lastTime) / nanoSec; //Intervalo de tempo para cada frame
            lastTime = now;
            if (delta >= 1) {
                tick();
                render();
                fps++;
                delta--;
            }

            //FPS debug
            if ((System.currentTimeMillis()-fpsTimer)  >= 1000) {
                System.out.println("FPS: " + fps);
                fps = 0;
                fpsTimer += 1000;
            }
        }
        stop(); //Garante que vai parar a execução dos processos do jogo caso saia do loop
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override //Pressionar tlecas
    public void keyPressed(KeyEvent e) {
        //Movimentação jogador
        int tecla = e.getKeyCode();
        if (Ferramentas.boolToInt((tecla == KeyEvent.VK_UP) || (tecla == KeyEvent.VK_W)) == 1) player.W = 1;
        if (Ferramentas.boolToInt((tecla == KeyEvent.VK_LEFT) || (tecla == KeyEvent.VK_A)) == 1) player.A = 1;
        if (Ferramentas.boolToInt((tecla == KeyEvent.VK_DOWN) || (tecla == KeyEvent.VK_S)) == 1) player.S = 1;
        if (Ferramentas.boolToInt((tecla == KeyEvent.VK_RIGHT) || (tecla == KeyEvent.VK_D)) == 1) player.D = 1;
    }

    @Override //Soltar teclas
    public void keyReleased(KeyEvent e) {
        //Movimentação jogador
        int tecla = e.getKeyCode();
        if (Ferramentas.boolToInt((tecla == KeyEvent.VK_UP) || (tecla == KeyEvent.VK_W)) == 1) player.W = 0;
        if (Ferramentas.boolToInt((tecla == KeyEvent.VK_LEFT) || (tecla == KeyEvent.VK_A)) == 1) player.A = 0;
        if (Ferramentas.boolToInt((tecla == KeyEvent.VK_DOWN) || (tecla == KeyEvent.VK_S)) == 1) player.S = 0;
        if (Ferramentas.boolToInt((tecla == KeyEvent.VK_RIGHT) || (tecla == KeyEvent.VK_D)) == 1) player.D = 0;
    }
}
