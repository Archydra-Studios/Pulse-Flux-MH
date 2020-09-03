#version 110

uniform sampler2D DiffuseSampler;
uniform sampler2D BeamSampler;
uniform sampler2D OverlaySampler;

uniform float BloomStrength;

varying vec2 texCoord;

void main(){
    vec4 texDiffuse = texture2D(DiffuseSampler, texCoord);
    vec4 texBeam = texture2D(BeamSampler, texCoord);
    vec4 texOverlay = texture2D(OverlaySampler, texCoord);
    gl_FragColor = vec4(mix(texDiffuse.rgb, texBeam.rgb, texBeam.a) + texOverlay.rgb * texOverlay.a * BloomStrength, 1.);
}