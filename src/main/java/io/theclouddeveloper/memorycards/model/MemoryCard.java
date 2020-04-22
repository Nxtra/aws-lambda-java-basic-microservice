package io.theclouddeveloper.memorycards.model;


public class MemoryCard {

    private String author;
    private String memoryText;


    public MemoryCard(String author, String memoryText) {
        this.author = author;
        this.memoryText = memoryText;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMemoryText() {
        return memoryText;
    }

    public void setMemoryText(String memoryText) {
        this.memoryText = memoryText;
    }

    @Override
    public String toString() {
        return "MemoryCard{" +
                "author='" + author + '\'' +
                ", memoryText='" + memoryText + '\'' +
                '}';
    }
}
