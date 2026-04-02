#version 150

uniform sampler2D DiffuseSampler;
uniform vec2 OutSize;
out vec4 fragColor;

void main() {
    fragColor = vec4(1.0, 0.0, 0.0, 1.0);
}
