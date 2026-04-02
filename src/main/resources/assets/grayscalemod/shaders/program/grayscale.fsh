#version 150

uniform sampler2D DiffuseSampler;
uniform float Progress;
in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec2 center = vec2(0.5, 0.5);
    vec2 uv = texCoord - center;

    // 强烈漩涡扭曲
    float dist = length(uv);
    float angle = Progress * 6.0 * (1.0 - dist);
    float s = sin(angle);
    float c = cos(angle);
    vec2 rotated = vec2(
        uv.x * c - uv.y * s,
        uv.x * s + uv.y * c
    ) + center;

    vec4 color = texture(DiffuseSampler, mix(texCoord, rotated, Progress));

    // 灰度（根据 Progress 渐变）
    float gray = dot(color.rgb, vec3(0.299, 0.587, 0.114));
    vec3 result = mix(color.rgb, vec3(gray), Progress);

    fragColor = vec4(result, color.a);
}
