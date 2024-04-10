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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.util.Random;
import java.util.Stack;

public class Aplicacao extends Application {
    AnchorPane pane;
    Button botao_gerar;
    Button botao_inicio;
    private Button[] vet;
    private Label[] labels;
    private Label labelI;
    private Label labelJ;
    private Label labelPermutacoes;

    private String code = "public void quickPods(int ini, int fim) {\n" +
            "    Stack<Integer> pilha = new Stack<>();\n" +
            "    pilha.push(fim);\n" +
            "    pilha.push(ini);\n" +
            "    while (!pilha.isEmpty()) {\n" +
            "        ini = pilha.pop();\n" +
            "        fim = pilha.pop();\n" +
            "        int i = ini, j = fim;\n" +
            "        Button aux;\n" +
            "        boolean flag = true;\n" +
            "        while (i < j) {\n" +
            "            if (flag)\n" +
            "                while (i < j && Integer.parseInt(vet[i].getText()) <= Integer.parseInt(vet[j].getText()))\n" +
            "                    i++;\n" +
            "            else\n" +
            "                while (i < j && Integer.parseInt(vet[j].getText()) >= Integer.parseInt(vet[i].getText()))\n" +
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

    public static void main(String[] args)
    {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Pesquisa e Ordenacao");
        pane = new AnchorPane();

        TextArea textArea = new TextArea(code);
        textArea.setEditable(false);
        textArea.setLayoutY(40);
        textArea.setLayoutX(400);
        pane.getChildren().add(textArea);
        highlightLine(textArea, 4);
        labelI = new Label("i");
        labelI.setVisible(false);
        labelJ = new Label("j");
        labelJ.setVisible(false);
        labelPermutacoes = new Label("Permutações: " + 0);
        labelPermutacoes.setLayoutX(10);
        labelPermutacoes.setLayoutY(130);
        labelPermutacoes.setVisible(false);
        pane.getChildren().add(labelPermutacoes);
        pane.getChildren().add(labelI);
        pane.getChildren().add(labelJ);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("HeapSort", "QuickSort");
        comboBox.setValue("HeapSort");
        comboBox.setLayoutX(10);
        comboBox.setLayoutY(70);
        pane.getChildren().add(comboBox);

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
    }

    private void highlightLine(TextArea textArea, int lineNumber) {
        String[] lines = textArea.getText().split("\n");
        StringBuilder highlightedCode = new StringBuilder();
        for (int i = 0; i < lines.length; i++) {
            if (i == lineNumber - 1) {
                highlightedCode.append(">> ").append(lines[i]).append("\n");
            } else {
                highlightedCode.append(lines[i]).append("\n");
            }
        }
        textArea.setText(highlightedCode.toString());
    }

    public void gerar_valores() {
        for(int i = 0; i < vet.length; i++) {
            vet[i].setText(Integer.toString(new Random().nextInt(100)));
            vet[i].setVisible(true);
            labels[i].setVisible(true);
            vet[i].setStyle("");
        }
        labelJ.setLayoutY(280);
        labelJ.setLayoutX(120 + 14 * 40);
        labelI.setLayoutY(280);
        labelI.setLayoutX(120 + 0 * 40);
        labelJ.setVisible(true);
        labelI.setVisible(true);
        labelPermutacoes.setVisible(true);
    }

    public void heap_sort()
    {
        Task<Void> task = new Task<Void>(){
            @Override
            protected Void call() {
                botao_gerar.setDisable(true);
                botao_inicio.setDisable(true);
                labelI.setVisible(false);
                labelJ.setVisible(false);
                int TL = vet.length, FE, FD, maiorF, pai, n = 0;
                Button aux;
                while (TL > 1) {
                    for(int x = 0; x < vet.length; x++)
                        if(isSort(x))
                            vet[x].setStyle("-fx-background-color: lightgreen;");
                        else
                            vet[x].setStyle("");
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
                            n++; // qtde permutações
                            int finalN = n;
                            Platform.runLater(() -> {
                                labelPermutacoes.setText("Permutações: " + finalN);
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
                        pai--;
                    }
                    aux = vet[0];
                    vet[0] = vet[TL - 1];
                    vet[TL - 1] = aux;
                    n++;
                    int finalN1 = n;
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
                    TL--;
                    for(int i = 0; i < vet.length; i++) {
                        int finalI = i;
                        Platform.runLater(() -> vet[finalI].setLayoutY(200));
                    }
                }
                botao_gerar.setDisable(false);
                botao_inicio.setDisable(false);
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

    public void quickIterativo(int ini, int fim) {
        Stack<Integer> pilha = new Stack<>();
        pilha.push(fim);
        pilha.push(ini);
        int n = 0;
        botao_gerar.setDisable(true);
        botao_inicio.setDisable(true);
        while (!pilha.isEmpty()) {
            ini = pilha.pop();
            fim = pilha.pop();

            int i = ini, j = fim;
            labelI.setLayoutX(115 + i * 40);
            labelJ.setLayoutX(125 + j * 40);
            Button aux;
            boolean flag = true;

            for(int x = 0; x < vet.length; x++)
                if(x >= i && x <= j)
                    vet[x].setVisible(true);
                else
                    vet[x].setVisible(false);

            while (i < j) {
                for(int x = 0; x < vet.length; x++)
                    if(isSort(x))
                        vet[x].setStyle("-fx-background-color: lightgreen;");

                if (flag)
                    while (i < j && Integer.parseInt(vet[i].getText()) <= Integer.parseInt(vet[j].getText())) {
                        i++;
                        labelI.setLayoutX(labelI.getLayoutX() + 40);
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                else
                    while (i < j && Integer.parseInt(vet[j].getText()) >= Integer.parseInt(vet[i].getText())){
                        j--;
                        labelJ.setLayoutX(labelJ.getLayoutX() - 40);
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

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
                final double posPai = buttonI.getLayoutX();
                final double posMaiorF = buttonJ.getLayoutX();
                final double diff = posMaiorF - posPai;

                for (double y = 0; Math.abs(y) <= Math.abs(diff); y += (diff > 0 ? 5 : -5)) {
                    final double posY = y;
                    Button finalButtonI = buttonI;
                    Button finalButtonJ = buttonJ;
                    Platform.runLater(() -> {
                        finalButtonI.setLayoutX(posPai + posY);
                        finalButtonJ.setLayoutX(posMaiorF - posY);
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

                aux = vet[i];
                vet[i] = vet[j];
                vet[j] = aux;
                n++; // qtde permutações
                int finalN = n;
                Platform.runLater(() -> {
                    labelPermutacoes.setText("Permutações: " + finalN);
                });

                flag = !flag;
            }
            if (ini < i - 1) {
                pilha.push(i - 1);
                pilha.push(ini);
            }
            if (j + 1 < fim) {
                pilha.push(fim);
                pilha.push(j + 1);
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for(int x = 0; x < vet.length; x++)
                vet[x].setVisible(true);
        labelI.setVisible(false);
        labelJ.setVisible(false);
        botao_gerar.setDisable(false);
        botao_inicio.setDisable(false);
    }

    public void quickPods(int ini, int fim) {
        Stack<Integer> pilha = new Stack<>();
        pilha.push(fim);
        pilha.push(ini);
        while (!pilha.isEmpty()) {
            ini = pilha.pop();
            fim = pilha.pop();
            int i = ini, j = fim;
            Button aux;
            boolean flag = true;
            while (i < j) {
                if (flag)
                    while (i < j && Integer.parseInt(vet[i].getText()) <= Integer.parseInt(vet[j].getText()))
                        i++;
                else
                    while (i < j && Integer.parseInt(vet[j].getText()) >= Integer.parseInt(vet[i].getText()))
                        j--;
                aux = vet[i];
                vet[i] = vet[j];
                vet[j] = aux;
                flag = !flag;
            }
            if (ini < i - 1) {
                pilha.push(i - 1);
                pilha.push(ini);
            }
            if (j + 1 < fim) {
                pilha.push(fim);
                pilha.push(j + 1);
            }
        }
    }
}