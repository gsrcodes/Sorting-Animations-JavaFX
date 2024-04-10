package com.example.sortanimation;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.util.Random;

public class Aplicacao extends Application {
    AnchorPane pane;
    Button botao_gerar;
    Button botao_inicio;
    Button botao_cancelar;
    private Button[] vet;
    boolean ordenando;

    public static void main(String[] args)
    {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Pesquisa e Ordenacao");
        pane = new AnchorPane();
        // ComboBox
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("HeapSort", "QuickSort");
        comboBox.setValue("HeapSort");
        comboBox.setLayoutX(10);
        comboBox.setLayoutY(70);
        pane.getChildren().add(comboBox);

        // Botão de início
        botao_inicio = new Button("Iniciar ordenação");
        botao_inicio.setLayoutX(10);
        botao_inicio.setLayoutY(100);
        botao_inicio.setOnAction(e -> {
            if(comboBox.getValue().equals("HeapSort"))
                heap_sort();
            else
                quick_sort();
        });
        pane.getChildren().add(botao_inicio);

        // Botão de gerar valores;
        botao_gerar = new Button("Gerar valores");
        botao_gerar.setLayoutX(130);
        botao_gerar.setLayoutY(100);
        botao_gerar.setOnAction(e -> gerar_valores());
        pane.getChildren().add(botao_gerar);

        // Botão de cancelar ordenacao;
        botao_cancelar = new Button("Cancelar");
        botao_cancelar.setLayoutX(400);
        botao_cancelar.setLayoutY(100);
        botao_cancelar.setOnAction(e -> ordenando = false);
        botao_cancelar.setVisible(false);
        pane.getChildren().add(botao_cancelar);

        // Array de botões
        int numBotoes = 15;
        vet = new Button[numBotoes];
        for (int i = 0; i < numBotoes; i++) {
            vet[i] = new Button();
            vet[i].setLayoutX(100 + i * 40); // Posicionamento horizontal
            vet[i].setLayoutY(200);
            vet[i].setMinHeight(40);
            vet[i].setMinWidth(40);
            vet[i].setFont(new Font(14));
            vet[i].setVisible(false);
            pane.getChildren().add(vet[i]);
        }

        Scene scene = new Scene(pane, 800, 300);
        stage.setScene(scene);
        stage.show();
    }
    public void gerar_valores() {
        for(int i = 0; i < vet.length; i++) {
            vet[i].setText(Integer.toString(new Random().nextInt(100)));
            vet[i].setVisible(true);
        }
    }

    public void heap_sort()
    {
        Task<Void> task = new Task<Void>(){
            @Override
            protected Void call() {
                ordenando = true;
                botao_gerar.setDisable(ordenando);
                botao_inicio.setDisable(ordenando);
                botao_cancelar.setVisible(ordenando);
                int TL = vet.length, FE, FD, maiorF, pai;
                Button aux;

                while (TL > 1 && ordenando) {
                    System.out.println(TL);
                    pai = TL / 2 - 1;
                    while (pai >= 0) {
                        FE = 2 * pai + 1;
                        FD = FE + 1;
                        maiorF = FE;

                        if (FD < TL && Integer.parseInt(vet[FD].getText()) > Integer.parseInt(vet[FE].getText()))
                            maiorF = FD;

                        if (Integer.parseInt(vet[maiorF].getText()) > Integer.parseInt(vet[pai].getText())) {
                            aux = vet[pai];
                            vet[pai] = vet[maiorF];
                            vet[maiorF] = aux;

                            for (int i = 0; i < 10; i++) {
                                int finalPai = pai;
                                Platform.runLater(() -> vet[finalPai].setLayoutY(vet[finalPai].getLayoutY() + 4.9));
                                int finalMaiorF = maiorF;
                                Platform.runLater(() -> vet[finalMaiorF].setLayoutY(vet[finalMaiorF].getLayoutY() - 4.9));
                                try {
                                    Thread.sleep(10);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            for (int i = 0; i < 1; i++) {
                                final int finalPai1 = pai;
                                final int finalMaiorF1 = maiorF;

                                double posPai = vet[finalPai1].getLayoutX();
                                double posMaiorF = vet[finalMaiorF1].getLayoutX();

                                double diff = posMaiorF - posPai;

                                if (diff > 0) {
                                    for (double j = 0; j <= diff; j += 5) {
                                        double finalJ = j;
                                        Platform.runLater(() -> vet[finalPai1].setLayoutX(posPai + finalJ));
                                        Platform.runLater(() -> vet[finalMaiorF1].setLayoutX(posMaiorF - finalJ));
                                        try {
                                            Thread.sleep(10);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } else {
                                    for (double j = 0; j >= diff; j -= 5) {
                                        double finalJ = j;
                                        Platform.runLater(() -> vet[finalPai1].setLayoutX(posPai + finalJ));
                                        Platform.runLater(() -> vet[finalMaiorF1].setLayoutX(posMaiorF - finalJ));
                                        try {
                                            Thread.sleep(10);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                            for (int i = 0; i < 10; i++) {
                                int finalPai2 = pai;
                                Platform.runLater(() -> vet[finalPai2].setLayoutY(vet[finalPai2].getLayoutY() - 4.9));
                                int finalMaiorF2 = maiorF;
                                Platform.runLater(() -> vet[finalMaiorF2].setLayoutY(vet[finalMaiorF2].getLayoutY() + 4.9));
                                try {
                                    Thread.sleep(10);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        pai--;
                    }
                    aux = vet[0];
                    vet[0] = vet[TL - 1];
                    vet[TL - 1] = aux;
                    for (int i = 0; i < 10; i++) {
                        int finalPai = 0;
                        Platform.runLater(() -> vet[finalPai].setLayoutY(vet[finalPai].getLayoutY() + 4.9));
                        int finalMaiorF = TL - 1;
                        Platform.runLater(() -> vet[finalMaiorF].setLayoutY(vet[finalMaiorF].getLayoutY() - 4.9));
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    for (int i = 0; i < 1; i++) {
                        final int finalPai1 = 0;
                        final int finalMaiorF1 = TL - 1;

                        double posPai = vet[finalPai1].getLayoutX();
                        double posMaiorF = vet[finalMaiorF1].getLayoutX();

                        double diff = posMaiorF - posPai;

                        if (diff > 0) {
                            for (double j = 0; j <= diff; j += 5) {
                                double finalJ = j;
                                Platform.runLater(() -> vet[finalPai1].setLayoutX(posPai + finalJ));
                                Platform.runLater(() -> vet[finalMaiorF1].setLayoutX(posMaiorF - finalJ));
                                try {
                                    Thread.sleep(10);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            for (double j = 0; j >= diff; j -= 5) {
                                double finalJ = j;
                                Platform.runLater(() -> vet[finalPai1].setLayoutX(posPai + finalJ));
                                Platform.runLater(() -> vet[finalMaiorF1].setLayoutX(posMaiorF - finalJ));
                                try {
                                    Thread.sleep(10);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    for (int i = 0; i < 10; i++) {
                        int finalPai2 = 0;
                        Platform.runLater(() -> vet[finalPai2].setLayoutY(vet[finalPai2].getLayoutY() - 4.9));
                        int finalMaiorF2 = TL - 1;
                        Platform.runLater(() -> vet[finalMaiorF2].setLayoutY(vet[finalMaiorF2].getLayoutY() + 4.9));
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    TL--;
                    for(int i = 0; i < vet.length; i++) {
                        int finalI = i;
                        Platform.runLater(() -> vet[finalI].setLayoutY(200));
                    }
                }
                ordenando = false;
                botao_gerar.setDisable(ordenando);
                botao_inicio.setDisable(ordenando);
                botao_cancelar.setVisible(ordenando);
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    public void quick_sort()
    {
        Task<Void> task = new Task<Void>(){
            @Override
            protected Void call() {
                System.out.println("Não implementado");
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }
}