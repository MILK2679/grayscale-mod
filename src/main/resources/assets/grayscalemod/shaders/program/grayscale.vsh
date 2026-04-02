#version 150

in vec4 Position;
out vec2 texCoord;
uniform mat4 ProjMat;
uniform vec2 InSize;

void main() {
    gl_Position = ProjMat * Position;
    texCoord = Position.xy / InSize;
}