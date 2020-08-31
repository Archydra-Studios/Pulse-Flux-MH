#version 110

uniform sampler2D DiffuseSampler;

// Time in seconds (+ tick delta)
uniform float tickTime;
uniform float fragAlpha;

varying vec2 texCoord;

void main() {
    float shift = texCoord.y * 5.;
    vec3 rainbow = vec3(sin(tickTime + shift) + 0.2, cos(tickTime + shift + 0.5) + 0.2, sin(tickTime + shift + 4.) + 0.2);
    vec4 tex = texture2D(DiffuseSampler, texCoord);
    vec4 color = min(tex * vec4(rainbow, 1.) * 3., vec4(1.)) + tex;
    gl_FragColor = color;
    gl_FragColor.a = fragAlpha;

}