#version 150

in vec4 Position;
out vec2 texCoord;
uniform mat4 ProjMat;
uniform vec2 OutSize;

void main() {
    vec4 pos = ProjMat * Position;
    gl_Position = pos;
    texCoord = Position.xy / OutSize;
}
