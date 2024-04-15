package com.example.sortanimation;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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
    private Label labelTL;
    private Label labeLExplicacao;

    private TextArea textArea;

    private TextArea pilhaValores;
    private String codigoQuick = "public void quickIterativo() {\n" +
            "     Pilha pilha = new Pilha();\n" +
            "     pilha.push(vet.length[]);\n" +
            "     pilha.push(0);\n" +
            "     while (!pilha.isEmpty()) {\n" +
            "        i = pilha.pop();\n" +
            "        j = pilha.pop();\n" +
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
            "            pilha.push(0);\n" +
            "        }\n" +
            "        if (j + 1 < fim) {\n" +
            "            pilha.push(vet.length);\n" +
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
        textArea.setLayoutX(1050);
        textArea.setMinHeight(580);
        textArea.setMaxWidth(300);
        textArea.setVisible(false);
        pane.getChildren().add(textArea);

        pilhaValores = new TextArea();
        pilhaValores.setEditable(false);
        pilhaValores.setLayoutY(40);
        pilhaValores.setLayoutX(950);
        pilhaValores.setMaxHeight(200);
        pilhaValores.setMaxWidth(70);
        pilhaValores.setVisible(false);
        pane.getChildren().add(pilhaValores);

        labelI = new Label("i");
        labelI.setFont(new Font(40));
        labelI.setVisible(false);
        labelJ = new Label("j");
        labelJ.setFont(new Font(40));
        labelJ.setVisible(false);
        labelPermutacoes = new Label("Permutações: " + 0);
        labelPermutacoes.setLayoutX(10);
        labelPermutacoes.setLayoutY(130);
        labelPermutacoes.setVisible(false);
        labelComparacoes = new Label("Comparações: " + 0);
        labelComparacoes.setLayoutX(10);
        labelComparacoes.setLayoutY(150);
        labelComparacoes.setVisible(false);
        labelTL = new Label("Tamanho lógico (TL): " + 0);
        labelTL.setLayoutX(10);
        labelTL.setLayoutY(170);
        labelTL.setVisible(false);
        labeLExplicacao = new Label(" ");
        labeLExplicacao.setLayoutX(10);
        labeLExplicacao.setLayoutY(500);
        labeLExplicacao.setVisible(false);
        labeLExplicacao.setFont(new Font(20));
        pane.getChildren().add(labelTL);
        pane.getChildren().add(labeLExplicacao);
        pane.getChildren().add(labelComparacoes);
        pane.getChildren().add(labelPermutacoes);
        pane.getChildren().add(labelI);
        pane.getChildren().add(labelJ);

        labelPai = new Label("pai");
        labelPai.setFont(new Font(20));
        labelPai.setVisible(false);
        labelFE = new Label("FE");
        labelFE.setFont(new Font(20));
        labelFE.setVisible(false);
        labelFD = new Label("FD");
        labelFD.setFont(new Font(20));
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

        int numBotoes = 10;
        Color corBorda = Color.BLACK;
        CornerRadii raioCanto = new CornerRadii(10);
        BorderStroke borderStroke = new BorderStroke(corBorda, BorderStrokeStyle.SOLID, raioCanto, null);
        Border borda = new Border(borderStroke);

        vet = new Button[numBotoes];
        labels = new Label[numBotoes];
        for (int i = 0; i < numBotoes; i++) {
            vet[i] = new Button();
            vet[i].setLayoutX(100 + i * 80);
            vet[i].setLayoutY(200);
            vet[i].setMinHeight(80);
            vet[i].setMinWidth(80);
            vet[i].setFont(new Font(20));
            vet[i].setVisible(false);
            vet[i].setBackground(Background.EMPTY);
            vet[i].setBorder(borda);

            labels[i] = new Label(Integer.toString(i));
            labels[i].setLayoutX(136 + i * 80);
            labels[i].setLayoutY(290);
            labels[i].setVisible(false);
            labels[i].setFont(new Font(20));
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
        labelJ.setLayoutY(320);
        labelJ.setLayoutX(120 + 14 * 40);
        labelI.setLayoutY(320);
        labelI.setLayoutX(120);
        labelFE.setLayoutX(140);
        labelFE.setLayoutY(360);
        labelPai.setLayoutX(140);
        labelPai.setLayoutY(320);
        labelFD.setLayoutX(140);
        labelFD.setLayoutY(400);
        labelPermutacoes.setVisible(true);
        labelComparacoes.setVisible(true);
    }

    public void heap_sort()
    {
        Task<Void> task = new Task<Void>(){
            int auxPR;
            @Override
            protected Void call() {
                textArea.setText(codigoHeap);
                textArea.setVisible(true);
                botao_gerar.setDisable(true);
                botao_inicio.setDisable(true);
                labelPermutacoes.setVisible(true);
                labelComparacoes.setVisible(true);
                labelTL.setVisible(true);
                labeLExplicacao.setVisible(true);
                Platform.runLater(() -> labeLExplicacao.setText("Apenas inicializando as variáveis que usaremos!"));
                indicaLinha(1, codigoHeap);
                int TL = vet.length, FE, FD, maiorF, pai, nPermutacao = 0, nComparacao = 0;
                int finalTL1 = TL;
                Platform.runLater(() -> {
                    labelTL.setText("Tamanho lógico (TL): " + finalTL1);
                });
                Platform.runLater(() -> labeLExplicacao.setText("TL é maior que 1? Se sim, entra na repetição"));
                indicaLinha(2, codigoHeap);
                while (TL > 1) {
                    Button aux;
                    labelPai.setVisible(true);
                    pai = TL / 2 - 1;
                    labelPai.setLayoutX(140 + pai * 80);
                    auxPR = pai;
                    Platform.runLater(() -> labeLExplicacao.setText("Pai recebe uma nova posição: " + auxPR));
                    indicaLinha(3, codigoHeap);

                    Platform.runLater(() -> labeLExplicacao.setText("Pai é maior ou igual a 0? Se sim entra na repetição"));
                    indicaLinha(4, codigoHeap);
                    while (pai >= 0) {
                        labelFE.setVisible(true);
                        FE = 2 * pai + 1;
                        labelFE.setLayoutX(140 + FE * 80);
                        auxPR = FE;
                        Platform.runLater(() -> labeLExplicacao.setText("O filho da esquerda recebe uma nova posição (pai * 2 + 1): " + auxPR));
                        indicaLinha(5, codigoHeap);

                        labelFD.setVisible(true);
                        FD = FE + 1;
                        labelFD.setLayoutX(140 + FD * 80);
                        auxPR = FD;
                        Platform.runLater(() -> labeLExplicacao.setText("O filho da direita recebe uma nova posição (FE + 1): " + auxPR));
                        indicaLinha(6, codigoHeap);

                        maiorF = FE;
                        Platform.runLater(() -> labeLExplicacao.setText("Qual é o maior filho?"));
                        indicaLinha(8, codigoHeap);
                        if(FD >= TL) {
                            Platform.runLater(() -> labeLExplicacao.setText("FD ainda não possui valor (ou possui um valor já ordenado), não iremos comparar os filhos ainda. Meu maior filho é o da esquerda"));
                            indicaLinha(8, codigoHeap);
                        }
                        else
                            if (Integer.parseInt(vet[FD].getText()) > Integer.parseInt(vet[FE].getText())){
                                maiorF = FD;
                                int finalFD = FD;
                                int finalFE = FE;
                                Platform.runLater(() -> labeLExplicacao.setText("O filho da direita é maior! " + vet[finalFD].getText() + " > " + vet[finalFE].getText()));
                                indicaLinha(8, codigoHeap);
                            }
                            else {
                                int finalFD = FD;
                                int finalFE = FE;
                                Platform.runLater(() -> labeLExplicacao.setText("O filho da esquerda é maior! " + vet[finalFE].getText() + " > " + vet[finalFD].getText()));
                                indicaLinha(9, codigoHeap);
                            }
                        nComparacao++;
                        auxPR = nComparacao;
                        Platform.runLater(() -> {
                            labelComparacoes.setText("Comparações: " + auxPR);
                        });
                        Platform.runLater(() -> labeLExplicacao.setText("O maior filho é maior que seu pai?"));
                        indicaLinha(10, codigoHeap);
                        if (Integer.parseInt(vet[maiorF].getText()) > Integer.parseInt(vet[pai].getText())) {
                            Platform.runLater(() -> labeLExplicacao.setText("Sim! Iremos então realizar a troca deles"));
                            indicaLinha3(11, 12, 13, codigoHeap);
                            aux = vet[pai];
                            vet[pai] = vet[maiorF];
                            vet[maiorF] = aux;
                            nPermutacao++; // qtde permutações
                            int auxPR = nPermutacao;
                            Platform.runLater(() -> {
                                labelPermutacoes.setText("Permutações: " + auxPR);
                            });
                            animacaoTroca(pai, maiorF);
                        }
                        else {
                            Platform.runLater(() -> labeLExplicacao.setText("Não é! Não faremos nada!"));
                            indicaLinha(10, codigoHeap);
                        }
                        nComparacao++;
                        auxPR = nComparacao;
                        Platform.runLater(() -> {
                            labelComparacoes.setText("Comparações: " + auxPR);
                        });
                        pai--;
                        int finalPai = pai;
                        Platform.runLater(() -> labeLExplicacao.setText("Pai agora assume um novo valor (valor antigo - 1): " + finalPai));
                        indicaLinha(15, codigoHeap);
                        for (int x = 0; x < 10; x++) {
                            final double offsetX = labelPai.getLayoutX() - 8;
                            Platform.runLater(() -> labelPai.setLayoutX(offsetX));
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        Platform.runLater(() -> labeLExplicacao.setText("Pai é maior ou igual a 0? Se sim volta na repetição"));
                        indicaLinha(4, codigoHeap);
                    }

                    nComparacao++;
                    auxPR = nComparacao;
                    Platform.runLater(() -> {
                        labelComparacoes.setText("Comparações: " + auxPR);
                    });

                    Platform.runLater(() -> labeLExplicacao.setText("Com pai = -1, iremos trocar a posição 0 com a de TL - 1!"));
                    indicaLinha3(17, 18, 19, codigoHeap);
                    aux = vet[0];
                    vet[0] = vet[TL - 1];
                    vet[TL - 1] = aux;
                    animacaoTroca(0, TL - 1);


                    nPermutacao++;
                    int PR = nPermutacao;
                    Platform.runLater(() -> {
                        labelPermutacoes.setText("Permutações: " + PR);
                    });

                    TL--;
                    int finalTL = TL;
                    Platform.runLater(() -> {
                        labelTL.setText("Tamanho lógico (TL): " + finalTL);
                    });
                    Platform.runLater(() -> labeLExplicacao.setText("TL agora assume um novo valor (antigo - 1): " + finalTL));
                    indicaLinha(20, codigoHeap);
                    Platform.runLater(() -> labeLExplicacao.setText(vet[finalTL].getText() + " agora está ordenado!"));
                    vet[TL].setStyle("-fx-background-color: lightgreen;");
                    indicaLinha(20, codigoHeap);
                    for(int i = 0; i < vet.length; i++) {
                        int finalI = i;
                        Platform.runLater(() -> vet[finalI].setLayoutY(200));
                    }
                    labelPai.setVisible(false);
                    labelFE.setVisible(false);
                    labelFD.setVisible(false);
                    Platform.runLater(() -> labeLExplicacao.setText("TL é maior que 1? Se sim, volta na repetição"));
                    indicaLinha(2, codigoHeap);
                }
                nComparacao++;
                auxPR = nComparacao;
                Platform.runLater(() -> {
                    labelComparacoes.setText("Comparações: " + auxPR);
                });
                botao_gerar.setDisable(false);
                botao_inicio.setDisable(false);
                for(int x = 0; x < vet.length; x++)
                        vet[x].setStyle("-fx-background-color: lightgreen;");
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    public void quick_sort() {
        Task<Void> task = new Task<Void>() {
            int auxPR;
            @Override
            protected Void call() throws InterruptedException {
                int nPermutacao = 0, nComparacao = 0;
                botao_gerar.setDisable(true);
                botao_inicio.setDisable(true);
                labeLExplicacao.setVisible(true);
                textArea.setText(codigoQuick);
                textArea.setVisible(true);
                pilhaValores.setVisible(true);
                Button aux;

                Platform.runLater(() -> {
                    labeLExplicacao.setText("Usaremos essa pilha para simular uma recursividade!");
                });
                Pilha pilha = new Pilha();
                indicaLinha(1, codigoQuick);

                Platform.runLater(() -> {
                    labeLExplicacao.setText("Inserindo na pilha o fim do vetor (tamanho do vetor - 1)");
                });
                pilha.push(vet.length - 1);
                mostraPilha(pilha);
                indicaLinha(2, codigoQuick);

                Platform.runLater(() -> {
                    labeLExplicacao.setText("Inserindo na pilha o inicio do vetor (0)");
                });
                pilha.push(0);
                mostraPilha(pilha);
                indicaLinha(3, codigoQuick);

                Platform.runLater(() -> {
                    labeLExplicacao.setText("Enquanto a pilha não estiver fazia, a repetição irá continuar");
                });
                indicaLinha(4, codigoQuick);
                while (!pilha.isEmpty()) {
                    nComparacao++;
                    auxPR = nComparacao;
                    Platform.runLater(() -> {
                        labelComparacoes.setText("Comparações: " + auxPR);
                    });

                    Platform.runLater(() -> {
                        labeLExplicacao.setText("i recebe o topo da pilha");
                    });
                    int i = pilha.pop();
                    mostraPilha(pilha);
                    labelI.setLayoutX(115 + i * 83);
                    labelI.setVisible(true);
                    indicaLinha(5, codigoQuick);

                    Platform.runLater(() -> {
                        labeLExplicacao.setText("j recebe o topo da pilha");
                    });
                    int j = pilha.pop();
                    mostraPilha(pilha);
                    labelJ.setLayoutX(125 + j * 83);
                    labelJ.setVisible(true);
                    indicaLinha(6, codigoQuick);

                    Platform.runLater(() -> {
                        labeLExplicacao.setText("flag, a variável de controle, recebe true");
                    });
                    boolean flag = true;
                    indicaLinha(7, codigoQuick);

                    Platform.runLater(() -> {
                        labeLExplicacao.setText("Somente o intervalo entre i e j será ordenado!");
                    });
                    for(int x = 0; x < vet.length; x++)
                        if(x >= i && x <= j)
                            vet[x].setVisible(true);
                        else
                            vet[x].setVisible(false);
                    Thread.sleep(4000);

                    Platform.runLater(() -> {
                        labeLExplicacao.setText("Enquanto i for maior que j, a repetição continuará");
                    });
                    indicaLinha(8, codigoQuick);
                    while (i < j) {
                        nComparacao++;
                        auxPR = nComparacao;
                        Platform.runLater(() -> {
                            labelComparacoes.setText("Comparações: " + auxPR);
                        });

                        boolean finalFlag = flag;
                        Platform.runLater(() -> {
                            labeLExplicacao.setText("A flag é true? Estado atual: " + finalFlag);
                        });
                        indicaLinha(9, codigoQuick);
                        if (flag) {
                            Platform.runLater(() -> {
                                labeLExplicacao.setText("Como a flag é verdadeira\n" +
                                        "Enquando i for menor que j e o valor em sua posição\n" +
                                        "for menor ou igual ao valor da posição de J, a repetição continuará");
                            });
                            indicaLinha(10, codigoQuick);
                            Thread.sleep(1000);
                            while (i < j && Integer.parseInt(vet[i].getText()) <= Integer.parseInt(vet[j].getText())) {
                                nComparacao++;
                                auxPR = nComparacao;
                                Platform.runLater(() -> {
                                    labelComparacoes.setText("Comparações: " + auxPR);
                                });
                                Platform.runLater(() -> {
                                    labeLExplicacao.setText("o valor de i está sendo incrementado em 1\n" +
                                            "Pois i ainda é menor que j\n" +
                                            "e o valor em sua posição também é menor que o valor na posição de j");
                                });
                                i++;
                                indicaLinha(11, codigoQuick);
                                for (int x = 0; x < 20; x++) {
                                    final Label finalLabel = labelI;
                                    Platform.runLater(() -> {
                                        finalLabel.setLayoutX(finalLabel.getLayoutX() + 4);
                                    });
                                    try {
                                        Thread.sleep(10);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                try {
                                    Thread.sleep(300);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            nComparacao++;
                            auxPR = nComparacao;
                            Platform.runLater(() -> {
                                labelComparacoes.setText("Comparações: " + auxPR);
                            });
                            indicaLinha(10, codigoQuick);
                        }
                        else {
                            Platform.runLater(() -> {
                                labeLExplicacao.setText("Como a flag é falsa\n" +
                                        "Enquando j for maior que i e o valor em sua posição\n" +
                                        "for maior ou igual ao valor da posição de i, a repetição continuará");
                            });
                            indicaLinha(12, codigoQuick);
                            while (i < j && Integer.parseInt(vet[j].getText()) >= Integer.parseInt(vet[i].getText())){
                                nComparacao++;
                                auxPR = nComparacao;
                                Platform.runLater(() -> {
                                    labelComparacoes.setText("Comparações: " + auxPR);
                                });

                                Platform.runLater(() -> {
                                    labeLExplicacao.setText("o valor de j está sendo decrementado em 1\n" +
                                            "Pois j ainda é maior que i\n" +
                                            "e o valor em sua posição também é maior que o valor na posição de i");
                                });
                                j--;
                                indicaLinha(13, codigoQuick);

                                for (int x = 0; x < 20; x++) {
                                    final Label finalLabel = labelJ;
                                    Platform.runLater(() -> {
                                        finalLabel.setLayoutX(finalLabel.getLayoutX() - 4);
                                    });
                                    try {
                                        Thread.sleep(10);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                try {
                                    Thread.sleep(300);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            nComparacao++;
                            auxPR = nComparacao;
                            Platform.runLater(() -> {
                                labelComparacoes.setText("Comparações: " + auxPR);
                            });
                        }
                        nComparacao++;
                        auxPR = nComparacao;
                        Platform.runLater(() -> {
                            labelComparacoes.setText("Comparações: " + auxPR);
                        });
                        Platform.runLater(() -> {
                            labeLExplicacao.setText("O valor na posição i é maior que o da posição J!!!");
                        });
                        Thread.sleep(2000);
                        Platform.runLater(() -> {
                            labeLExplicacao.setText("Os valores das posições i e j serão trocados!");
                        });
                        aux = vet[i];
                        vet[i] = vet[j];
                        vet[j] = aux;
                        indicaLinha3(16, 17, 18, codigoQuick);
                        animacaoTroca(i, j);
                        nPermutacao = nPermutacao + 1; // qtde permutações
                        auxPR = nPermutacao;
                        Platform.runLater(() -> {
                            labelPermutacoes.setText("Permutações: " + auxPR);
                        });
                        Platform.runLater(() -> {
                            labeLExplicacao.setText("Flag recebe o inverso dela, sinalizando que é a vez da outra parte andar!");
                        });
                        flag = !flag;
                        indicaLinha(19, codigoQuick);
                        Platform.runLater(() -> {
                            labeLExplicacao.setText("Caso i ainda for maior que j, a repetição continuará");
                        });
                        indicaLinha(8, codigoQuick);
                    }
                    nComparacao++;
                    auxPR = nComparacao;
                    Platform.runLater(() -> {
                        labelComparacoes.setText("Comparações: " + auxPR);
                    });
                    Platform.runLater(() -> {
                        labeLExplicacao.setText("i - 1 é maior que o inicio do vetor (0)?");
                    });
                    indicaLinha(21, codigoQuick);
                    if (0 < i - 1) {
                        Platform.runLater(() -> {
                            labeLExplicacao.setText("Sim, então i - 1 e o inicio do vetor (0) irão para a pilha!");
                        });
                        indicaLinha(22, codigoQuick);
                        pilha.push(i - 1);
                        mostraPilha(pilha);
                        indicaLinha(23, codigoQuick);
                        pilha.push(0);
                        mostraPilha(pilha);
                    }
                    nComparacao++;
                    auxPR = nComparacao;
                    Platform.runLater(() -> {
                        labelComparacoes.setText("Comparações: " + auxPR);
                    });

                    Platform.runLater(() -> {
                        labeLExplicacao.setText("j + 1 é menor que o tamanho do vetor - 1?");
                    });
                    indicaLinha(8, codigoQuick);
                    if (j + 1 < vet.length - 1) {
                        Platform.runLater(() -> {
                            labeLExplicacao.setText("Sim, então j + 1 e o tamanho do vetor - 1 irão para a pilha!");
                        });
                        indicaLinha(26, codigoQuick);
                        pilha.push(vet.length - 1);
                        mostraPilha(pilha);
                        indicaLinha(27, codigoQuick);
                        pilha.push(j + 1);
                        mostraPilha(pilha);

                        Platform.runLater(() -> {
                            labeLExplicacao.setText("Caso a pilha não estiver fazia, a repetição irá continuar");
                        });
                        indicaLinha(4, codigoQuick);
                    }
                    nComparacao++;
                    auxPR = nComparacao;
                    Platform.runLater(() -> {
                        labelComparacoes.setText("Comparações: " + auxPR);
                    });
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for(int x = 0; x < vet.length; x++)
                        if(isSort(x))
                            vet[x].setStyle("-fx-background-color: lightgreen;");
                }
                nComparacao++;
                auxPR = nComparacao;
                Platform.runLater(() -> {
                    labelComparacoes.setText("Comparações: " + auxPR);
                });
                mostraPilha(pilha);
                for(int x = 0; x < vet.length; x++)
                    vet[x].setVisible(true);
                labelI.setVisible(false);
                labelJ.setVisible(false);
                botao_gerar.setDisable(false);
                botao_inicio.setDisable(false);
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
            if (i == nLinha)
                novoTexto.append(">>").append(linhas[i].substring(5));
            else
                novoTexto.append(linhas[i]);
            novoTexto.append("\n");
        }
        textArea.setText(novoTexto.toString());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        textArea.setText(texto);
    }
    private void indicaLinha3(int nLinha, int nLinha2, int nLinha3, String texto) {
        String[] linhas = textArea.getText().split("\n");
        StringBuilder novoTexto = new StringBuilder();
        for (int i = 0; i < linhas.length; i++) {
            if (i == nLinha || i == nLinha2 || i == nLinha3)
                novoTexto.append(">>").append(linhas[i].substring(5));
            else
                novoTexto.append(linhas[i]);
            novoTexto.append("\n");
        }
        textArea.setText(novoTexto.toString());

        try {
            Thread.sleep(1000);
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

    public void animacaoTroca(int botao1, int botao2) {
        for (int i = 0; i < 20; i++) {
            Platform.runLater(() -> vet[botao1].setLayoutY(vet[botao1].getLayoutY() + 4.9));
            Platform.runLater(() -> vet[botao2].setLayoutY(vet[botao2].getLayoutY() - 4.9));
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 1; i++) {
            double posBt1 = vet[botao1].getLayoutX();
            double posBt2 = vet[botao2].getLayoutX();

            double diff = posBt2 - posBt1;

            if (diff > 0) {
                for (double j = 0; j <= diff; j += 5) {
                    double finalJ = j;
                    Platform.runLater(() -> vet[botao1].setLayoutX(posBt1 + finalJ));
                    Platform.runLater(() -> vet[botao2].setLayoutX(posBt2 - finalJ));
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                for (double j = 0; j >= diff; j -= 5) {
                    double finalJ = j;
                    Platform.runLater(() -> vet[botao1].setLayoutX(posBt1 + finalJ));
                    Platform.runLater(() -> vet[botao2].setLayoutX(posBt2 - finalJ));
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        for (int i = 0; i < 20; i++) {
            Platform.runLater(() -> vet[botao1].setLayoutY(vet[botao1].getLayoutY() - 4.9));
            Platform.runLater(() -> vet[botao2].setLayoutY(vet[botao2].getLayoutY() + 4.9));
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
