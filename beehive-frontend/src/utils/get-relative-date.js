export const getRelativeDate = (date) => {
  const date1 = new Date();
  const date2 = new Date(date);
  const timeDifference = Math.abs(date2 - date1);

  let differenceInMinutes = Math.ceil(timeDifference / (1000 * 60));
  let units = differenceInMinutes < 2 ? "just now" : "minutes ago";

  if (differenceInMinutes > 60) {
    differenceInMinutes = (differenceInMinutes / 60).toFixed();
    units = "hours ago";
    if (differenceInMinutes > 24 && differenceInMinutes <= 168) {
      differenceInMinutes = (differenceInMinutes / 24).toFixed();
      units = differenceInMinutes > 1 ? "days ago" : "day ago";
    } else if (differenceInMinutes > 168 && differenceInMinutes <= 8760) {
      differenceInMinutes = (differenceInMinutes / 168).toFixed();
      units = differenceInMinutes > 1 ? "weeks ago" : "week ago";
    } else if (differenceInMinutes > 8760) {
      differenceInMinutes = (differenceInMinutes / 8760).toFixed();
      units = differenceInMinutes > 1 ? "years ago" : "year ago";
    }
  }

  if (units === "just now") {
    return units;
  }

  return `${differenceInMinutes} ${units}`;
};
