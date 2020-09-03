#version 120

uniform vec2 InSize;

varying vec2 texCoord;
varying vec2 oneTexel;

void main(){
    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
    oneTexel = 1.0 / InSize;
    texCoord = vec2(gl_MultiTexCoord0);
}