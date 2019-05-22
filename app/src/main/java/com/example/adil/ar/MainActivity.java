package com.example.adil.ar;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ArFragment arFragment;
    private ModelRenderable bearRenderable,catRenderable,pistonrendable;
    ImageView bear,cat,piston;
    View arryView[];
    ViewRenderable name_animal;
    int selected =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arFragment=(ArFragment)getSupportFragmentManager().findFragmentById(R.id.sceneform_ux_fragment);
        bear=(ImageView)findViewById(R.id.bear);
        cat=(ImageView)findViewById(R.id.cat);
        piston=(ImageView)findViewById(R.id.piston);


        setArrayView();
        setClickListerner();
        setupmodel();

        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {

                Anchor anchor=hitResult.createAnchor();
                AnchorNode anchorNode=new AnchorNode(anchor);
                anchorNode.setParent(arFragment.getArSceneView().getScene());
                createModel(anchorNode,selected);

            }
        });
    }

    private void setupmodel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ModelRenderable.builder().
                    setSource(this,R.raw.bear)
                    .build().thenAccept(renderable ->bearRenderable=renderable )
                    .exceptionally(
                            throwable -> {
                                Toast.makeText(this, "unable to load bear", Toast.LENGTH_SHORT).show();
                                return null;
                            }
                    );
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ModelRenderable.builder().
                    setSource(this,R.raw.cat)
                    .build().thenAccept(renderable ->catRenderable=renderable )
                    .exceptionally(
                            throwable -> {
                                Toast.makeText(this, "unable to load cat", Toast.LENGTH_SHORT).show();
                                return null;
                            }
                    );
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ModelRenderable.builder().
                    setSource(this,R.raw.piston)
                    .build().thenAccept(renderable ->pistonrendable=renderable )
                    .exceptionally(
                            throwable -> {
                                Toast.makeText(this, "unable to load piston", Toast.LENGTH_SHORT).show();
                                return null;
                            }
                    );
        }
    }




    private void createModel(AnchorNode anchorNode, int selected) {
        if(selected==1)
        {
            TransformableNode bear=new TransformableNode(arFragment.getTransformationSystem());
            bear.setParent(anchorNode);
            bear.setRenderable(bearRenderable);
            bear.select();
        }
        if(selected==2)
        {
            TransformableNode cat=new TransformableNode(arFragment.getTransformationSystem());
            cat.setParent(anchorNode);
            cat.setRenderable(catRenderable);
            cat.select();
        }
        if(selected==3)
        {
            TransformableNode piston=new TransformableNode(arFragment.getTransformationSystem());
            piston.setParent(anchorNode);
            piston.setRenderable(pistonrendable);
            piston.select();
        }


    }

    private void setClickListerner() {
        for (int i=0; i<arryView.length;i++)
            arryView[i].setOnClickListener(this);
    }

    private void setArrayView() {
        arryView =new View[]{
                bear,cat
        };
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.bear ) {
            selected = 1;
        }
        else if(v.getId()==R.id.cat )
        {
            selected =2;

        }
        else if(v.getId()==R.id.piston )
        {
            selected =3;

        }
    }


}