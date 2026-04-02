#version 150

in vec4 Position;
uniform mat4 ProjMat;

void main() {
    gl_Position = ProjMat * Position;
}
