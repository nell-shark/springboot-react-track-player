import {AddTrack} from '@components/AddTrack';
import {Page} from '@interfaces/page';
import {TrackList} from '@components/TrackList';
import {useTitle} from '@hooks/useTitle';
import {useTracks} from "@hooks/useTracks";
import {ShowMore} from "@components/ShowMore";

export interface TracksProps extends Page {
}

export function Tracks({title}: TracksProps) {
  useTitle(title);
  const {loading, error, tracks, addTrack, showMore, hideShowMore} = useTracks();

  return (
    <>
      <TrackList loading={loading} error={error} tracks={tracks}/>
      <div className="mt-4 d-flex justify-content-center gap-3">
        <ShowMore showMore={showMore} hide={hideShowMore} />
        <AddTrack addTrack={addTrack}/>
      </div>
    </>
  );
}
