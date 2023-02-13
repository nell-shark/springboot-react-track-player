import { Footer } from "@components/Footer";
import { Navigation } from "@components/Navigation";
import { TracksList } from "@components/TracksList";
import { useTitle } from "@hooks/useTitle";
import { Page } from "@interfaces/page";

export interface TracksPageProps extends Page{
}

export function TracksPage({ title }: TracksPageProps) {
    useTitle(title);

    return (
        <>
            <Navigation />
            <TracksList />
            <Footer />
        </>
    );
}
