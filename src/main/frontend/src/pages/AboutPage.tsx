import { Footer } from "@components/Footer";
import { Navigation } from "@components/Navigation";
import { useTitle } from "@hooks/useTitle";
import { Page } from "@interfaces/page";

interface AboutPageProps extends Page{
}

export function AboutPage({ title }: AboutPageProps) {
    useTitle(title);

    return (
        <>
            <Navigation />
            <p>123</p>
            <Footer />
        </>
    );
}