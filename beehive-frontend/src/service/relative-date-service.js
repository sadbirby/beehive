export const getRelativeDate = (date) => {
    const date1 = new Date();
    const date2 = new Date(date);
    const timeDifference = Math.abs(date2 - date1);

    var differenceInMinutes = Math.ceil(timeDifference / (1000 * 60));
    var units = differenceInMinutes < 1 ? "minute" : "minutes";

    if (differenceInMinutes > 60) {
        differenceInMinutes = (differenceInMinutes / 60).toFixed();
        units = "hours"
        if (differenceInMinutes > 24) {
            differenceInMinutes = (differenceInMinutes / 24).toFixed();
            units = differenceInMinutes > 1 ? "days" : "day"
        }
    }

    return `${differenceInMinutes} ${units} ago`;
}