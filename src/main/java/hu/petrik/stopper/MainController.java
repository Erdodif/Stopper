package hu.petrik.stopper;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class MainController {
    public Label stopper;
    @FXML
    private GridPane mainGrid;
    @FXML
    private Button btnStartStop;
    @FXML
    private Button btnResetReszido;
    @FXML
    private ListView<String> reszidoLista;
    private boolean onrun;
    private Timer stopperTimer;
    private LocalDateTime time;
    private Duration storedTime;

    @FXML
    public void initialize(){
        onrun = false;
    }

    @FXML
    public void startStopClick(ActionEvent actionEvent) {
        if(onrun){
            stopperStop();
        }
        else{
            stopperStart();
        }
    }


    private void stopperStart() {
        btnStartStop.setText("Stop");
        if(!stopper.getText().equals("00:00.000")){
            String[] pmp = stopper.getText().split(":");
            time = LocalDateTime.now().minus(storedTime);
        }
        else{
            time = LocalDateTime.now();
        }
        btnResetReszido.setText("Reszido");
        onrun = true;
        stopperTimer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Duration elteltIdo = Duration.between(time,LocalDateTime.now());
                int ezred = elteltIdo.toMillisPart();
                int masodperc = elteltIdo.toSecondsPart();
                int perc = elteltIdo.toMinutesPart();
                Platform.runLater(() -> stopper.setText(String.format("%02d:%02d.%03d",perc,masodperc,ezred)));
            }
        };
        stopperTimer.scheduleAtFixedRate(task,1,1);
    }

    private void stopperStop() {
        btnStartStop.setText("Resume");
        btnResetReszido.setText("Reset");
        stopperTimer.cancel();
        storedTime = Duration.between(time,LocalDateTime.now());
        onrun = false;
    }

    @FXML
    public void resetReszidoClick(ActionEvent actionEvent) {
        if(onrun){
            reszido();
        }
        else{
            reset();
        }
    }

    private void reset() {
        btnStartStop.setText("Start");
        stopper.setText("00:00.000");
        reszidoLista.getItems().clear();
    }

    private void reszido() {
        String reszido = stopper.getText();
        reszidoLista.getItems().add(reszido);
    }
}