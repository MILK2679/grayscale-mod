#version 150

in vec4 Position;
out vec2 texCoord;
uniform mat4 ProjMat;

void main() {
    gl_Position = ProjMat * Position;
    texCoord = vec2(gl_Position.x * 0.5 + 0.5, gl_Position.y * 0.5 + 0.5);
}
