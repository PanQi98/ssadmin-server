package edu.ssadmin.server.utils;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class SensitiveWordFilter {

    private TrieNode root = new TrieNode();

    // 添加敏感词到 Trie 树
    public void addSensitiveWord(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            node.children.computeIfAbsent(ch, k -> new TrieNode());
            node = node.children.get(ch);
        }
        node.isEnd = true;
    }

    // 过滤敏感词
    public String filter(String text) {
        StringBuilder result = new StringBuilder();
        TrieNode current = root;
        int start = 0;

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            if (current.children.containsKey(ch)) {
                current = current.children.get(ch);
                if (current.isEnd) {
                    // 匹配到敏感词，替换为 *
                    result.append(replaceWithAsterisks(text.substring(start, i + 1)));
                    start = i + 1;
                    current = root;
                }
            } else {
                current = root;
            }
        }

        result.append(text.substring(start));

        return result.toString();
    }

    private String replaceWithAsterisks(String word) {
        StringBuilder asterisks = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            asterisks.append("*");
        }
        return asterisks.toString();
    }

    private static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean isEnd;
    }
}

