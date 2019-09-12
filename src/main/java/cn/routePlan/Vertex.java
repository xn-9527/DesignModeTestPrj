package cn.routePlan;

public class Vertex implements Comparable<Vertex> {

    private Long id;
    private Long weight;

    public Vertex(Long id, Long weight) {
        super();
        this.id = id;
        this.weight = weight;
    }

    public Long getId() {
        return id;
    }

    public Long getWeight() {
        return weight;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((weight == null) ? 0 : weight.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vertex other = (Vertex) obj;
        if (weight == null) {
            if (other.weight != null)
                return false;
        } else if (!weight.equals(other.weight))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Vertex [id=" + id + ", weight=" + weight + "]";
    }

    @Override
    public int compareTo(Vertex o) {
        if (this.weight < o.weight)
            return -1;
        else if (this.weight > o.weight)
            return 1;
        else
            return this.getId().compareTo(o.getId());
    }

}