package spider;

public class MovieEntity {
    private String MovieID;
    private String MovieName;
    private String Director;
    private String LeadingActors;
    private String actors;
    private String MovieType;
    private String ReleaseTime;
    private String Versionsurls;

    public String getVersions() {
        return Versionsurls;
    }

    public String getActors() {
        return actors;
    }

    public String getDirector() {
        return Director;
    }

    public String getLeadingActors() {
        return LeadingActors;
    }

    public String getMovieID() {
        return MovieID;
    }

    public String getMovieName() {
        return MovieName;
    }

    public String getMovieType() {
        return MovieType;
    }

    public String getReleaseTime() {
        return ReleaseTime;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public void setLeadingActors(String leadingActors) {
        LeadingActors = leadingActors;
    }

    public void setMovieID(String movieID) {
        MovieID = movieID;
    }

    public void setMovieName(String movieName) {
        MovieName = movieName;
    }

    public void setMovieType(String movieType) {
        MovieType = movieType;
    }

    public void setReleaseTime(String releaseTime) {
        ReleaseTime = releaseTime;
    }

    public void setVersions(String versionsurls) {
        Versionsurls = versionsurls;
    }

    @Override
    public String toString() {
        return "MovieID:"+getMovieID()+"\n"+"MovieName:"+getMovieName()+"\n"+"Director:"+getDirector()+"\n"+"LeadingActors:"
                +getLeadingActors()+"\n"+"actors:"+getActors()+"\n"+"MovieType:"+getMovieType()+"\n"+"ReleaseTime"+getReleaseTime()+"\n"+"Versionsurls:"
                +getVersions()+"\n";
    }
}
