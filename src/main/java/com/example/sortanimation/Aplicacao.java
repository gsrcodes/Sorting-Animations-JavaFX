package com.example.sortanimation;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.util.Random;

public class Aplicacao extends Application {
    AnchorPane pane;
    Button botao_gerar;
    Button botao_inicio;
    private Button[] vet;
    private Label[] labels;
    private Label labelI;
    private Label labelJ;
    private Label labelFE;
    private Label labelFD;
    private Label labelPai;
    private Label labelPermutacoes;
    private Label labelComparacoes;

    private TextArea textArea;

    private TextArea pilhaValores;

    private String codigoQuick = "public void quickIterativo(int ini, int fim) {\n" +
            "    Pilha pilha = new Pilha();\n" +
            "    pilha.push(fim);\n" +
            "    pilha.push(ini);\n" +
            "    while (!pilha.isEmpty()) {\n" +
            "        ini = pilha.pop();\n" +
            "        fim = pilha.pop();\n" +
            "        int i = ini, j = fim;\n" +
            "        boolean flag = true;\n" +
            "        while (i < j) {\n" +
            "            if (flag)\n" +
            "                while (i < j && vet[i] <= vet[j]\n" +
            "                    i++;\n" +
            "            else\n" +
            "                while (i < j && vet[j] >= vet[i])\n" +
            "                    j--;\n" +
            "            aux = vet[i];\n" +
            "            vet[i] = vet[j];\n" +
            "            vet[j] = aux;\n" +
            "            flag = !flag;\n" +
            "        }\n" +
            "        if (ini < i - 1) {\n" +
            "            pilha.push(i - 1);\n" +
            "            pilha.push(ini);\n" +
            "        }\n" +
            "        if (j + 1 < fim) {\n" +
            "            pilha.push(fim);\n" +
            "            pilha.push(j + 1);\n" +
            "        }\n" +
            "    }\n" +
            "}\n";

    private String codigoHeap = "public void heapSort() {\n" +
            "        int TL = vet.length, FE, FD, maiorF, pai, n = 0;\n" +
            "        while (TL > 1) {\n" +
            "            pai = TL / 2 - 1;\n" +
            "            while (pai >= 0) {\n" +
            "                FE = 2 * pai + 1;\n" +
            "                FD = FE + 1;\n" +
            "                maiorF = FE;\n" +
            "                if (FD < TL && vet[FD] > vet[FE])\n" +
            "                    maiorF = FD;\n" +
            "                if (vet[maiorF] > vet[pai]) {\n" +
            "                    aux = vet[pai];\n" +
            "                    vet[pai] = vet[maiorF];\n" +
            "                    vet[maiorF] = aux;\n" +
            "                }\n" +
            "                pai--;\n" +
            "            }\n" +
            "            aux = vet[0];\n" +
            "            vet[0] = vet[TL - 1];\n" +
            "            vet[TL - 1] = aux;\n" +
            "            TL--;\n" +
            "        }\n" +
            "    }\n";

    public static void main(String[] args)
    {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Pesquisa e Ordenacao");
        pane = new AnchorPane();

        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setLayoutY(40);
        textArea.setLayoutX(850);
        textArea.setMinHeight(580);
        textArea.setMaxWidth(300);
        textArea.setVisible(false);
        pane.getChildren().add(textArea);

        pilhaValores = new TextArea();
        pilhaValores.setEditable(false);
        pilhaValores.setLayoutY(40);
        pilhaValores.setLayoutX(750);
        pilhaValores.setMaxHeight(200);
        pilhaValores.setMaxWidth(70);
        pilhaValores.setVisible(false);
        pane.getChildren().add(pilhaValores);

        labelI = new Label("i");
        labelI.setVisible(false);
        labelJ = new Label("j");
        labelJ.setVisible(false);
        labelPermutacoes = new Label("Permutações: " + 0);
        labelPermutacoes.setLayoutX(10);
        labelPermutacoes.setLayoutY(130);
        labelPermutacoes.setVisible(false);
        labelComparacoes = new Label("Comparações: " + 0);
        labelComparacoes.setLayoutX(10);
        labelComparacoes.setLayoutY(150);
        labelComparacoes.setVisible(false);
        pane.getChildren().add(labelComparacoes);
        pane.getChildren().add(labelPermutacoes);
        pane.getChildren().add(labelI);
        pane.getChildren().add(labelJ);

        labelPai = new Label("pai");
        labelPai.setVisible(false);
        labelFE = new Label("FE");
        labelFE.setVisible(false);
        labelFD = new Label("FD");
        labelFD.setVisible(false);
        pane.getChildren().add(labelPai);
        pane.getChildren().add(labelFE);
        pane.getChildren().add(labelFD);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("HeapSort", "QuickSort");
        comboBox.setValue("HeapSort");
        comboBox.setLayoutX(10);
        comboBox.setLayoutY(70);
        pane.getChildren().add(comboBox);

        botao_inicio = new Button("Iniciar ordenação");
        botao_inicio.setLayoutX(10);
        botao_inicio.setLayoutY(100);
        botao_inicio.setDisable(true);
        botao_inicio.setOnAction(e -> {
            if(comboBox.getValue().equals("HeapSort"))
                heap_sort();
            else
                quick_sort();
        });
        pane.getChildren().add(botao_inicio);

        botao_gerar = new Button("Gerar valores");
        botao_gerar.setLayoutX(130);
        botao_gerar.setLayoutY(100);
        botao_gerar.setOnAction(e -> gerar_valores());
        pane.getChildren().add(botao_gerar);

        int numBotoes = 15;
        vet = new Button[numBotoes];
        labels = new Label[numBotoes];
        for (int i = 0; i < numBotoes; i++) {
            vet[i] = new Button();
            vet[i].setLayoutX(100 + i * 40);
            vet[i].setLayoutY(200);
            vet[i].setMinHeight(40);
            vet[i].setMinWidth(40);
            vet[i].setFont(new Font(14));
            vet[i].setVisible(false);

            labels[i] = new Label(Integer.toString(i));
            labels[i].setLayoutX(118 + i * 40);
            labels[i].setLayoutY(250);
            labels[i].setVisible(false);
            pane.getChildren().add(labels[i]);
            pane.getChildren().add(vet[i]);
        }

        Scene scene = new Scene(pane, 800, 300);
        stage.setScene(scene);
        stage.show();
        stage.setFullScreen(true);
    }

    public void gerar_valores() {
        for(int i = 0; i < vet.length; i++) {
            vet[i].setText(Integer.toString(new Random().nextInt(100)));
            vet[i].setVisible(true);
            labels[i].setVisible(true);
            vet[i].setStyle("");
        }
        botao_inicio.setDisable(false);
        labelJ.setLayoutY(280);
        labelJ.setLayoutX(120 + 14 * 40);
        labelI.setLayoutY(280);
        labelI.setLayoutX(120);
        labelFE.setLayoutX(115);
        labelFE.setLayoutY(300);
        labelPai.setLayoutX(115);
        labelPai.setLayoutY(280);
        labelFD.setLayoutX(115);
        labelFD.setLayoutY(320);
        labelPermutacoes.setVisible(true);
        labelComparacoes.setVisible(true);
    }

    public void heap_sort()
    {
        Task<Void> task = new Task<Void>(){
            @Override
            protected Void call() {
                textArea.setText(codigoHeap);
                textArea.setVisible(true);
                botao_gerar.setDisable(true);
                botao_inicio.setDisable(true);
                labelPai.setVisible(true);
                labelFE.setVisible(true);
                labelFD.setVisible(true);
                labelPermutacoes.setVisible(true);
                labelComparacoes.setVisible(true);
                indicaLinha(2, codigoHeap);
                int TL = vet.length, FE, FD, maiorF, pai, nPermutacao = 0, nComparacao = 0;
                Button aux;
                indicaLinha(3, codigoHeap);
                while (TL > 1) {
                    nComparacao++;
                    int finalNComparacao = nComparacao;
                    Platform.runLater(() -> {
                        labelComparacoes.setText("Comparações: " + finalNComparacao);
                    });

                    indicaLinha(3, codigoHeap);
                    for(int x = 0; x < vet.length; x++)
                        if(isSort(x))
                            vet[x].setStyle("-fx-background-color: lightgreen;");
                        else
                            vet[x].setStyle("");
                    indicaLinha(4, codigoHeap);
                    pai = TL / 2 - 1;
                    labelPai.setLayoutX(115 + pai * 40);
                    indicaLinha(5, codigoHeap);
                    while (pai >= 0) {
                        nComparacao++;
                        int finalNComparacao1 = nComparacao;
                        Platform.runLater(() -> {
                            labelComparacoes.setText("Comparações: " + finalNComparacao1);
                        });
                        indicaLinha(5, codigoHeap);

                        indicaLinha(6, codigoHeap);
                        FE = 2 * pai + 1;
                        labelFE.setLayoutX(115 + FE * 40);
                        indicaLinha(7, codigoHeap);
                        FD = FE + 1;
                        labelFD.setLayoutX(115 + FD * 40);
                        indicaLinha(8, codigoHeap);
                        maiorF = FE;

                        indicaLinha(9, codigoHeap);
                        if (FD < TL && Integer.parseInt(vet[FD].getText()) > Integer.parseInt(vet[FE].getText())){
                            nComparacao++;
                            int finalNComparacao2 = nComparacao;
                            Platform.runLater(() -> {
                                labelComparacoes.setText("Comparações: " + finalNComparacao2);
                            });
                            maiorF = FD;
                            indicaLinha(9, codigoHeap);
                        }
                        nComparacao++;
                        int finalNComparacao3 = nComparacao;
                        Platform.runLater(() -> {
                            labelComparacoes.setText("Comparações: " + finalNComparacao3);
                        });

                        indicaLinha(10, codigoHeap);
                        if (Integer.parseInt(vet[maiorF].getText()) > Integer.parseInt(vet[pai].getText())) {
                            nComparacao++;
                            int finalNComparacao4 = nComparacao;
                            Platform.runLater(() -> {
                                labelComparacoes.setText("Comparações: " + finalNComparacao4);
                            });
                            indicaLinha(11, codigoHeap);
                            aux = vet[pai];
                            indicaLinha(12, codigoHeap);
                            vet[pai] = vet[maiorF];
                            indicaLinha(14, codigoHeap);
                            vet[maiorF] = aux;
                            nPermutacao++; // qtde permutações
                            int finalN = nPermutacao;
                            Platform.runLater(() -> {
                                labelPermutacoes.setText("Comparações: " + finalN);
                            });
                            for (int i = 0; i < 10; i++) {
                                int finalPai = pai;
                                Platform.runLater(() -> vet[finalPai].setLayoutY(vet[finalPai].getLayoutY() + 4.9));
                                int finalMaiorF = maiorF;
                                Platform.runLater(() -> vet[finalMaiorF].setLayoutY(vet[finalMaiorF].getLayoutY() - 4.9));
                                try {
                                    Thread.sleep(2);
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
                                            Thread.sleep(2);
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
                                            Thread.sleep(2);
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
                                    Thread.sleep(2);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        nComparacao++;
                        int finalNComparacao5 = nComparacao;
                        Platform.runLater(() -> {
                            labelComparacoes.setText("Comparações: " + finalNComparacao5);
                        });
                        indicaLinha(16, codigoHeap);
                        pai--;
                        labelPai.setLayoutX(115 + pai * 40);
                    }
                    nComparacao++;
                    int finalNComparacao6 = nComparacao;
                    Platform.runLater(() -> {
                        labelComparacoes.setText("Comparações: " + finalNComparacao6);
                    });
                    indicaLinha(17, codigoHeap);
                    aux = vet[0];
                    indicaLinha(18, codigoHeap);
                    vet[0] = vet[TL - 1];
                    indicaLinha(19, codigoHeap);
                    vet[TL - 1] = aux;
                    nPermutacao++;
                    int finalN1 = nPermutacao;
                    Platform.runLater(() -> {
                        labelPermutacoes.setText("Permutações: " + finalN1);
                    });
                    for (int i = 0; i < 10; i++) {
                        int finalPai = 0;
                        Platform.runLater(() -> vet[finalPai].setLayoutY(vet[finalPai].getLayoutY() + 4.9));
                        int finalMaiorF = TL - 1;
                        Platform.runLater(() -> vet[finalMaiorF].setLayoutY(vet[finalMaiorF].getLayoutY() - 4.9));
                        try {
                            Thread.sleep(2);
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
                                    Thread.sleep(2);
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
                                    Thread.sleep(2);
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
                            Thread.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    indicaLinha(21, codigoHeap);
                    TL--;
                    for(int i = 0; i < vet.length; i++) {
                        int finalI = i;
                        Platform.runLater(() -> vet[finalI].setLayoutY(200));
                    }
                }
                nComparacao++;
                int finalNComparacao7 = nComparacao;
                Platform.runLater(() -> {
                    labelComparacoes.setText("Comparações: " + finalNComparacao7);
                });
                botao_gerar.setDisable(false);
                botao_inicio.setDisable(false);
                labelPai.setVisible(false);
                labelFE.setVisible(false);
                labelFD.setVisible(false);
                for(int x = 0; x < vet.length; x++)
                    if(isSort(x))
                        vet[x].setStyle("-fx-background-color: lightgreen;");
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    public void quick_sort() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                quickIterativo(0, vet.length - 1);
                return null;
            }
        };

        task.setOnSucceeded(e -> {
            Platform.runLater(() -> {
                for(int i = 0; i < vet.length; i++)
                    System.out.println(vet[i].getText());
            });
        });

        Thread thread = new Thread(task);
        thread.start();
    }

    public boolean isSort(int pos) {
        for(int i = 0; i < pos; i++)
            if(Integer.parseInt(vet[i].getText()) > Integer.parseInt(vet[pos].getText()))
                return false;
        for(int i = pos + 1; i < vet.length; i++)
            if(Integer.parseInt(vet[i].getText()) < Integer.parseInt(vet[pos].getText()))
                return false;
        return true;
    }

    private void indicaLinha(int nLinha, String texto) {
        String[] linhas = textArea.getText().split("\n");
        StringBuilder novoTexto = new StringBuilder();
        for (int i = 0; i < linhas.length; i++) {
            if (i == nLinha - 1) {
                novoTexto.append(">>").append(linhas[i]).append("\n");
            } else {
                novoTexto.append(linhas[i]).append("\n");
            }
        }
        textArea.setText(novoTexto.toString());

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        textArea.setText(texto);
    }

    public void mostraPilha(Pilha pilha) {
        Pilha pilhaAux = new Pilha(pilha);
        pilhaValores.setText("Pilha:\n");
        while (!pilhaAux.isEmpty()) {
            pilhaValores.setText(pilhaValores.getText() + pilhaAux.pop() + "\n");
        }
    }

    public void quickIterativo(int ini, int fim) {
        labelJ.setVisible(true);
        labelI.setVisible(true);
        textArea.setText(codigoQuick);
        textArea.setVisible(true);
        pilhaValores.setVisible(true);
        indicaLinha(2, codigoQuick);
        Pilha pilha = new Pilha();
        indicaLinha(3, codigoQuick);
        pilha.push(fim);
        mostraPilha(pilha);
        indicaLinha(4, codigoQuick);
        pilha.push(ini);
        mostraPilha(pilha);
        int nPermutacao = 0, nComparacao = 0;
        botao_gerar.setDisable(true);
        botao_inicio.setDisable(true);
        indicaLinha(5, codigoQuick);
        while (!pilha.isEmpty()) {
            nComparacao++;
            int finalNComparacao = nComparacao;
            Platform.runLater(() -> {
                labelComparacoes.setText("Comparações: " + finalNComparacao);
            });
            mostraPilha(pilha);
            indicaLinha(5, codigoQuick);
            indicaLinha(6, codigoQuick);
            ini = pilha.pop();
            mostraPilha(pilha);
            indicaLinha(7, codigoQuick);
            fim = pilha.pop();
            mostraPilha(pilha);
            indicaLinha(8, codigoQuick);
            int i = ini, j = fim;
            labelI.setLayoutX(115 + i * 40);
            labelJ.setLayoutX(125 + j * 40);
            Button aux;
            indicaLinha(9, codigoQuick);
            boolean flag = true;

            for(int x = 0; x < vet.length; x++)
                if(x >= i && x <= j)
                    vet[x].setVisible(true);
                else
                    vet[x].setVisible(false);

            indicaLinha(10, codigoQuick);
            while (i < j) {
                nComparacao++;
                int finalNComparacao1 = nComparacao;
                Platform.runLater(() -> {
                    labelComparacoes.setText("Comparações: " + finalNComparacao1);
                });
                indicaLinha(10, codigoQuick);
                for(int x = 0; x < vet.length; x++)
                    if(isSort(x))
                        vet[x].setStyle("-fx-background-color: lightgreen;");

                indicaLinha(11, codigoQuick);
                if (flag) {
                    indicaLinha(12, codigoQuick);
                    while (i < j && Integer.parseInt(vet[i].getText()) <= Integer.parseInt(vet[j].getText())) {
                        nComparacao++;
                        int finalNComparacao3 = nComparacao;
                        Platform.runLater(() -> {
                            labelComparacoes.setText("Comparações: " + finalNComparacao3);
                        });
                        indicaLinha(12, codigoQuick);
                        indicaLinha(13, codigoQuick);
                        i++;
                        labelI.setLayoutX(labelI.getLayoutX() + 40);
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    nComparacao++;
                    int finalNComparacao4 = nComparacao;
                    Platform.runLater(() -> {
                        labelComparacoes.setText("Comparações: " + finalNComparacao4);
                    });
                    indicaLinha(11, codigoQuick);
                }
                else {
                    indicaLinha(14, codigoQuick);
                    indicaLinha(15, codigoQuick);
                    while (i < j && Integer.parseInt(vet[j].getText()) >= Integer.parseInt(vet[i].getText())){
                        nComparacao++;
                        int finalNComparacao5 = nComparacao;
                        Platform.runLater(() -> {
                            labelComparacoes.setText("Comparações: " + finalNComparacao5);
                        });
                        indicaLinha(15, codigoQuick);
                        indicaLinha(16, codigoQuick);
                        j--;
                        labelJ.setLayoutX(labelJ.getLayoutX() - 40);
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    nComparacao++;
                    int finalNComparacao6 = nComparacao;
                    Platform.runLater(() -> {
                        labelComparacoes.setText("Comparações: " + finalNComparacao6);
                    });
                }
                nComparacao++;
                int finalNComparacao2 = nComparacao;
                Platform.runLater(() -> {
                    labelComparacoes.setText("Comparações: " + finalNComparacao2);
                });
                indicaLinha(17, codigoQuick);
                aux = vet[i];
                indicaLinha(18, codigoQuick);
                vet[i] = vet[j];
                indicaLinha(19, codigoQuick);
                vet[j] = aux;
                for (int x = 0; x < 10; x++) {
                    final int finalI = i;
                    final int finalJ = j;
                    final Button buttonI = vet[finalI];
                    final Button buttonJ = vet[finalJ];
                    Platform.runLater(() -> {
                        buttonI.setLayoutY(buttonI.getLayoutY() + 4.9);
                        buttonJ.setLayoutY(buttonJ.getLayoutY() - 4.9);
                    });

                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Button buttonI = vet[i];
                Button buttonJ = vet[j];
                final double posI = buttonI.getLayoutX();
                final double posJ = buttonJ.getLayoutX();
                final double diff = posJ - posI;

                for (double y = 0; Math.abs(y) <= Math.abs(diff); y += (diff > 0 ? 5 : -5)) {
                    final double posY = y;
                    Button finalButtonI = buttonI;
                    Button finalButtonJ = buttonJ;
                    Platform.runLater(() -> {
                        finalButtonI.setLayoutX(posI + posY);
                        finalButtonJ.setLayoutX(posJ - posY);
                    });
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int x = 0; x < 10; x++) {
                    final int finalI = i;
                    final int finalJ = j;
                    buttonI = vet[finalI];
                    buttonJ = vet[finalJ];

                    Button finalButtonI1 = buttonI;
                    Button finalButtonJ1 = buttonJ;
                    Platform.runLater(() -> {
                        finalButtonI1.setLayoutY(finalButtonI1.getLayoutY() - 4.9);
                        finalButtonJ1.setLayoutY(finalButtonJ1.getLayoutY() + 4.9);
                    });
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                nPermutacao = nPermutacao + 1; // qtde permutações
                int finalN = nPermutacao;
                Platform.runLater(() -> {
                    labelPermutacoes.setText("Permutações: " + finalN);
                });
                indicaLinha(20, codigoQuick);
                flag = !flag;
            }
            nComparacao++;
            int finalNComparacao7 = nComparacao;
            Platform.runLater(() -> {
                labelComparacoes.setText("Comparações: " + finalNComparacao7);
            });
            indicaLinha(22, codigoQuick);
            if (ini < i - 1) {
                indicaLinha(23, codigoQuick);
                pilha.push(i - 1);
                mostraPilha(pilha);
                indicaLinha(24, codigoQuick);
                pilha.push(ini);
                mostraPilha(pilha);
            }
            nComparacao++;
            int finalNComparacao8 = nComparacao;
            Platform.runLater(() -> {
                labelComparacoes.setText("Comparações: " + finalNComparacao8);
            });
            if (j + 1 < fim) {
                indicaLinha(27, codigoQuick);
                pilha.push(fim);
                mostraPilha(pilha);
                indicaLinha(28, codigoQuick);
                pilha.push(j + 1);
                mostraPilha(pilha);
            }
            nComparacao++;
            int finalNComparacao9 = nComparacao;
            Platform.runLater(() -> {
                labelComparacoes.setText("Comparações: " + finalNComparacao9);
            });
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        nComparacao++;
        int finalNComparacao10 = nComparacao;
        Platform.runLater(() -> {
            labelComparacoes.setText("Comparações: " + finalNComparacao10);
        });
        mostraPilha(pilha);
        for(int x = 0; x < vet.length; x++)
                vet[x].setVisible(true);
        labelI.setVisible(false);
        labelJ.setVisible(false);
        botao_gerar.setDisable(false);
        botao_inicio.setDisable(false);
    }
}