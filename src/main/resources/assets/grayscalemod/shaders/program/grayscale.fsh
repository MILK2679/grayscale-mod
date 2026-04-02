#version 150

uniform sampler2D DiffuseSampler;
uniform float Progress;
in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec2 center = vec2(0.5, 0.5);
    vec2 uv = texCoord - center;
    float dist = length(uv);
    float angle = Progress * 5.0 * (1.0 - dist * 1.5);
    float s = sin(angle);
    float c = cos(angle);
    vec2 rotated = vec2(uv.x * c - uv.y * s, uv.x * s + uv.y * c) + center;
    vec2 sampleUV = mix(texCoord, rotated, Progress);
    vec4 color = texture(DiffuseSampler, sampleUV);
    float gray = dot(color.rgb, vec3(0.299, 0.587, 0.114));
    fragColor = vec4(mix(color.rgb, vec3(gray), Progress), color.a);
}
