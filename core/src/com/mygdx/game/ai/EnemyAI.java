package com.mygdx.game.ai;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;

import java.io.IOException;
import java.util.ArrayList;

public class EnemyAI {
    private static Handler handler;
    private ASPInputProgram input;
    private String path;
    private String dlv_input;
    public EnemyAI() throws IOException {
        path = "./desktop/build/resources/main/dlv2.exe";
        //path = "/core/libs/dlv2.exe";
        dlv_input = "./desktop/build/resources/main/input_enemy.dlv";
        //System.out.println(dlv_input);
        //dlv_input = "input_player.dlv";
        String current = new java.io.File( "." ).getCanonicalPath();
        //System.out.println("Current dir:"+current);
        handler = new DesktopHandler(new DLV2DesktopService(path));
        input = new ASPInputProgram();
        input.addFilesPath(dlv_input);
    }
    public void clear()
    {
        //handler = new DesktopHandler(new DLV2DesktopService(this.path));
        //handler.removeAll();
        handler.removeAll();
        input = new ASPInputProgram();
        input.addFilesPath(dlv_input);
    }
    public void load_fact(Position pos, ArrayList<Action> actions, ArrayList<Distance> dists) throws Exception {


        input.addObjectInput(pos);
        for(Action action : actions)
            input.addObjectInput(action);
        for(Distance dist : dists)
            input.addObjectInput(dist);


        handler.addProgram(input);

    }
    public AnswerSets getAnswerSets() throws Exception {
        Output o = handler.startSync();
        try {
            for(int i=0; ; i++){
                System.out.println("input dlv = " + handler.getInputProgram(i).getPrograms());
            }}catch (Exception ignore){}
        if (((AnswerSets) o).getAnswersets().size() == 0) {
            throw new Exception("NO ANSWERSET!");
        }
        for (AnswerSet an : ((AnswerSets) o).getAnswersets()) {
            System.out.println("output = " + an.getAnswerSet());
        }

        return (AnswerSets) o;

    }
}
