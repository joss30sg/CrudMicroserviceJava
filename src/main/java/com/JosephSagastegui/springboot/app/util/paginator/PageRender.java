package com.JosephSagastegui.springboot.app.util.paginator;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;

import com.InterviewJosephSagastegui.springboot.app.util.paginator.PageItem;

public class PageRender<T> {
    private String url;
    private Page<T> page;
    private int totalPaginas;
    private int numElementosPorPagina;
    private int paginaActual;
    private int desde;
    private int hasta;
    private List<PageItem> paginas;

    public PageRender(String url, Page<T> page) {
        this.url = url;
        this.page = page;
        this.numElementosPorPagina = 6;
        this.totalPaginas = page.getTotalPages();
        this.paginaActual = page.getNumber() + 1;
        this.paginas = new ArrayList<>();

        if (this.totalPaginas <= this.numElementosPorPagina) {
            this.desde = 1;
            this.hasta = this.totalPaginas;
        } else if (this.paginaActual <= this.numElementosPorPagina / 2) {
            this.desde = 1;
            this.hasta = this.numElementosPorPagina;
        } else if (this.paginaActual >= this.totalPaginas - this.numElementosPorPagina / 2) {
            this.desde = this.totalPaginas - this.numElementosPorPagina + 1;
            this.hasta = this.totalPaginas;
        } else {
            this.desde = this.paginaActual - this.numElementosPorPagina / 2;
            this.hasta = this.paginaActual + this.numElementosPorPagina / 2 - 1;
        }

        for (int i = 0; i < this.hasta - this.desde + 1; i++) {
            this.paginas.add(new PageItem(this.desde + i, this.paginaActual == this.desde + i));
        }
    }

    public String getUrl() {
        return url;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public int getPaginaActual() {
        return paginaActual;
    }

    public List<PageItem> getPaginas() {
        return paginas;
    }

    public boolean isFirst() {
        return page.isFirst();
    }

    public boolean isLast() {
        return page.isLast();
    }

    public boolean isHasNext() {
        return page.hasNext();
    }

    public boolean isHasPrevious() {
        return page.hasPrevious();
    }
}