package com.example.projet_ni;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SwipeManager extends AppCompatActivity {
    String orientation = "";
    String link= "";
    SwipeManager(View v){       //Constructeur
        int seuil = 100;            //Valeurs de la Distance minimale du swipe
        int velocite_seuil = 100;    //Seuil de vitesse du Swipe

        GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDown(MotionEvent e) {      //équivalent du OnClick pour le SwipeManager
                //return super.onDown(e);
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {      //Méthode de Gestion du Swipe
                float xDiff = e2.getX() - e1.getX();        //(Coordonnées axe x) Point de départ Swipe - Point d'Arrivée du Swipe
                float yDiff = e2.getY() - e1.getY();        //(Coordonnées axe y) Point de départ Swipe - Point d'Arrivée du Swipe
                Context mainContext = v.getContext();       //Récupération du Context de l'Activité
                try {
                    if (Math.abs(xDiff) > Math.abs(yDiff)){                                     //Boucle de Gestion Swipes Horizontaux
                        if (Math.abs(xDiff) > seuil && Math.abs(velocityX) > velocite_seuil){               //Gestion de la vitesse et distance du Swipe pour éviter l'activation accidentelle
                            if (xDiff < 0 && orientation == "left"){                                                //Gestion du Swipe LEFT pour changer d'Activity  || Si on a fixé l'orientation à 'left'
                                Toast.makeText(mainContext, "Accès à la Page Web", Toast.LENGTH_SHORT).show();
                                Intent webIntent = new Intent(mainContext, WebActivity.class);    //Intent pour aller à l'Activité de la Cam
                                if (!link.equals("")) {
                                    webIntent.putExtra("LINK", link);
                                }
                                mainContext.startActivity(webIntent);                                //Syntaxe Adaptative pour fonctionner dans tous les cas
                            }
                            else if (xDiff > 0 && orientation == "right"){                                          //Gestion du Swipe RIGHT pour changer d'Activity  || Si on a fixé l'orientation à 'right'
                                Toast.makeText(mainContext, "Retour à la Question", Toast.LENGTH_SHORT).show();
                                Intent quizzIntent = new Intent(mainContext, MainActivity.class);       //Intent pour aller à l'Activité de la Cam
                                mainContext.startActivity(quizzIntent);                                 //Syntaxe Adaptative pour fonctionner dans tous les cas
                            }
                            else

                            return true;
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return false;
            }
        };
        GestureDetector gestureDetector = new GestureDetector(listener);        //Création d'un GestureDetector avec les caractéristique de notre objet 'listener'
        v.setOnTouchListener(new View.OnTouchListener() {                       //Gestion
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {        //Du click
                return gestureDetector.onTouchEvent(motionEvent);               //De notre 'gestureDetector' || Sans ce code les commandes des méthodes de notre 'listener' ne sont pas traitées
            }
        });
    }

    public void setSwipeOrientation(String orientation){        //Choix de la Direction du Swipe
        this.orientation = orientation;
    }

    public void setURLToOpen(String url) {
        this.link= url;
    }

}
